package com.example.demo.controller;


import com.example.demo.dto.*;
import com.example.demo.dto.ride.RideDTO;
import com.example.demo.dto.ride.RideDriverDTO;
import com.example.demo.dto.ride.RidePassengerDTO;
import com.example.demo.dto.ride.RidePathDTO;
import com.example.demo.dto.user.*;
import com.example.demo.exceptions.AccountNotActivatedException;
import com.example.demo.util.email.EmailDetails;
import com.example.demo.util.email.EmailService;
import com.example.demo.exceptions.FailedPasswordResetException;
import com.example.demo.exceptions.PasswordNotMatchingException;
import com.example.demo.exceptions.UserIdNotMatchingException;
import com.example.demo.model.*;
import com.example.demo.repository.DriverRepository;
import com.example.demo.repository.PassengerRepository;
import com.example.demo.service.*;
import com.example.demo.security.JwtTokenUtil;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@Validated
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private JwtTokenUtil tokenUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;
    @Autowired
    RideService rideService;
    @Autowired
    EmailService emailService;
    @Autowired
    PassengerService passengerService;
    @Autowired
    DriverService driverService;
    @Autowired
    RejectionMessageService rejectionMessageService;
    @Autowired
    RouteService routeService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private PassengerRepository passengerRepository;

    @PreAuthorize("hasAuthority('ROLE_PASSENGER') || hasAuthority('ROLE_DRIVER')")
    @PutMapping(value="/{id}/changePassword",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@PathVariable(value = "id", required = true) @NotNull Integer id,
                                            @Valid @RequestBody ChangePasswordDTO request,
                                            Principal userPrincipal){
        User user = userService.findOneById(id);
        if(!userPrincipal.getName().equals(user.getEmail())){
            throw new UserIdNotMatchingException();
        }
        if(!userService.passwordEncoderUser().matches(request.getOldPassword(),user.getPassword())){
            throw new PasswordNotMatchingException();
        }
        user.setPassword(request.getNewPassword());
        userService.saveEncode(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //TODO Kad formiramo front dio, link do stranice za resetovanje ukljuciti u mail
    @PreAuthorize("hasAuthority('ROLE_PASSENGER') || hasAuthority('ROLE_DRIVER')")
    @GetMapping(value="/{id}/resetPassword", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendEmailForPasswordChange(@PathVariable(value = "id", required = true) @NotNull  Integer id,
                                                        Principal userPrincipal) {
        User user = userService.findOneById(id);
        if(!userPrincipal.getName().equals(user.getEmail())){
            throw new UserIdNotMatchingException();
        }
        Random rand = new Random();
        int code = rand.nextInt(9000000) + 1000000;
        Date expirationDate = Date.valueOf(LocalDate.now().plusDays(1));
        user.setCode(code);
        user.setExpirationDAte(expirationDate);
        userService.save(user);

        String body = "Hello,\n"
                + "You have requested to reset your password.\n"
                + "Use the code below to change your password:\n"
                + "\n" + code + "\n"
                + "\nThe code will expire in 1 day.\n\n "
                + "\nIgnore this email if you do remember your password,\n "
                + "or you have not made the request.";

        String subject = "Password reset request";
        EmailDetails emailDetails = new EmailDetails(user.getEmail(),body, subject);
        emailService.sendSimpleMail(emailDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PreAuthorize("hasAuthority('ROLE_PASSENGER') || hasAuthority('ROLE_DRIVER')")
    @PutMapping(value="/{id}/resetPassword",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> resetPassword(@PathVariable(value = "id", required = true) @NotNull  Integer id,
                                           @Valid @RequestBody ResetPasswordDTO request,
                                           Principal userPrincipal){
        User user = userService.findOneById(id);
        if(!userPrincipal.getName().equals(user.getEmail())){
            throw new UserIdNotMatchingException();
        }
        String code = user.getCode().toString();
        Date expirationDate = user.getExpirationDAte();
        if(!code.equals(request.getCode()) || Date.valueOf(LocalDate.now()).after(expirationDate)){
            throw new FailedPasswordResetException();
        }
        user.setPassword(request.getNewPassword());
        user.setCode(null);
        user.setExpirationDAte(null);
        userService.saveEncode(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //TODO Vjerovatno admin endpoint
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MultipleDTO> getUsers(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        List<UserResponseDTO> users = new ArrayList<UserResponseDTO>();
        List<User> userList;
        if (page != null && size != null) {
            userList = userService.findAll(page, size);
        } else {
            userList = userService.findAll();
        }

        for (User u : userList) {
            users.add(new UserResponseDTO(u));
        }
        MultipleDTO response = new MultipleDTO();
        response.setResults(users);
        response.setTotalCount(users.size());
        return new ResponseEntity<MultipleDTO>(response, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_PASSENGER') || hasAuthority('ROLE_DRIVER')")
    @GetMapping(value = "/{id}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRides(
            @PathVariable(value = "id", required = true) @NotNull  Integer id,
            @RequestParam(required = false) @Min(0) Integer page,
            @RequestParam(required = false) @Min(0) Integer size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            Principal userPrincipal) {

        User user = userService.findOneById(id);
        if(!userPrincipal.getName().equals(user.getEmail())){
            throw new UserIdNotMatchingException();
        }

        List<Ride> rides = null;
        if(user.getRole().equals("DRIVER")){
            rides = rideService.getRides(id, page, size, sort, from, to);
        }else{
            rides = rideService.getRidesPassenger(id, page, size, sort, from, to);
        }

        MultipleDTO response = new MultipleDTO();
        List<RideDTO> rideList = new ArrayList<>();

        for(Ride ride: rides){
            RideDriverDTO driver = new RideDriverDTO(ride.getDriver());

            List<RidePassengerDTO> passengerListDTO = new ArrayList<>();
            List<Passenger> passengerList = passengerService.getPassengersOfRide(ride.getId());
            for(Passenger passenger: passengerList){
                passengerListDTO.add(new RidePassengerDTO(passenger));
            }
            RejectionMessage message = rejectionMessageService.getMessageFromRide(ride.getId());
            RejectionDTO rejection = new RejectionDTO(message);

            List<RidePathDTO> pathListDTO = new ArrayList<>();
            List<Route> pathList = routeService.getRoutesFromRide(ride.getId());
            for(Route route: pathList){
                pathListDTO.add(new RidePathDTO(route));
            }

            rideList.add(new RideDTO(ride, driver, passengerListDTO, rejection, pathListDTO));
        }

        response.setResults(rideList);
        response.setTotalCount(rideList.size());
        return new ResponseEntity<MultipleDTO>(response, HttpStatus.OK);
    }
    @PostMapping(value="/login",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserLoginResponseDTO> logIn(@Valid @RequestBody UserLoginRequestDTO request){
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword());
        Authentication auth = authenticationManager.authenticate(authReq);

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);

        User user = userService.getUser(request.getEmail());
        if(!user.isActive()){
            throw new AccountNotActivatedException();
        }
        String token = tokenUtils.generateToken(request.getEmail(),sc.getAuthentication().getAuthorities().toArray()[0].toString(),user.getId()); // prosledjujemo email, role i id korisnika
        String refreshToken = tokenUtils.generateRefreshToken(request.getEmail(),sc.getAuthentication().getAuthorities().toArray()[0].toString(),user.getId());

        UserLoginResponseDTO jwt = new UserLoginResponseDTO();
        jwt.setAccessToken(token);
        jwt.setRefreshToken(refreshToken);

        return new ResponseEntity<UserLoginResponseDTO>(jwt, HttpStatus.OK);

    }
    @PreAuthorize("hasAuthority('ROLE_PASSENGER') || hasAuthority('ROLE_DRIVER')")
    @GetMapping(value = "/{id}/message", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMessages(
            @PathVariable(value = "id", required = true) @NotNull  Integer id,
            Principal userPrincipal) {
        User user = userService.findOneById(id);
        if(!userPrincipal.getName().equals(user.getEmail())){
            throw new UserIdNotMatchingException();
        }
        MultipleDTO response = new MultipleDTO();
        List<UserMessageResponseDTO> messageDTOS = new ArrayList<>();
        List<Message> messages = messageService.findAllById(id);
        if(messages != null) {
            for (Message m : messages) {
                messageDTOS.add(new UserMessageResponseDTO(m));
            }
        }
        response.setResults(messageDTOS);
        response.setTotalCount(messageDTOS.size());
        return new ResponseEntity<MultipleDTO>(response, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_PASSENGER') || hasAuthority('ROLE_DRIVER')")
    @PostMapping(value = "/{id}/message", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendMessage(@PathVariable(value = "id", required = true) @NotNull  Integer id,
                                         @Valid @RequestBody UserMessageRequestDTO request,
                                         Principal userPrincipal){
        String mail = userPrincipal.getName();
        User sender = userService.findSenderByEmail(mail);
        if(!userPrincipal.getName().equals(sender.getEmail())){
            throw new UserIdNotMatchingException();
        }
        User receiver = userService.findReceiverById(id);
        Ride ride  =  rideService.findOneById(request.getRideId());
        Message message = new Message(request, Time.valueOf(LocalTime.now()),sender, receiver);
        messageService.save(message);
        UserMessageResponseDTO response = new UserMessageResponseDTO(message);
        response.setId(id);
        return new ResponseEntity<UserMessageResponseDTO>(response, HttpStatus.OK);
    }





    //------------------------------------------------------ADMIN-----------------------------------------------------
    //ADMIN Endpoint
//    @PutMapping(value = "/{id}/block")
//    public ResponseStatusException blockUser(@PathVariable(value = "id", required = true) Integer id) throws Exception {
//        return new ResponseStatusException(HttpStatus.NO_CONTENT);
//    }
//    //ADMIN Endpoint
//    @PutMapping(value = "/{id}/unblock")
//    public ResponseStatusException unblockUser(@PathVariable(value = "id", required = true) Integer id) throws Exception {
//        return new ResponseStatusException(HttpStatus.NO_CONTENT);
//    }
//    //ADMIN Endpoint
//    @PostMapping(value = "/{id}/note", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<UserNoteResponseDTO> createNote(@PathVariable(value = "id", required = true) Integer id,
//                                                          @RequestBody UserNoteRequestDTO request) throws Exception {
//        UserNoteResponseDTO response = new UserNoteResponseDTO();
//        response.setId(id);
//        return new ResponseEntity<UserNoteResponseDTO>(response, HttpStatus.OK);
//    }
//    //ADMIN Endpoint
//    @GetMapping(value = "/{id}/note", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<MultipleMessagesDTO> getNotes(
//            @PathVariable(value = "id", required = true) Integer id,
//            @RequestParam(required = false) Integer page,
//            @RequestParam(required = false) Integer size) {
//        MultipleMessagesDTO response = new MultipleMessagesDTO();
//        List<UserNoteResponseDTO> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            list.add(new UserNoteResponseDTO());
//        }
//        response.setResults(list);
//        return new ResponseEntity<MultipleMessagesDTO>(response, HttpStatus.OK);
//    }
}


