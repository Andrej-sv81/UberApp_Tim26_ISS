package com.example.demo.controller;


import com.example.demo.dto.*;
import com.example.demo.dto.ride.RideDTO;
import com.example.demo.dto.ride.RideDriverDTO;
import com.example.demo.dto.ride.RidePassengerDTO;
import com.example.demo.dto.ride.RidePathDTO;
import com.example.demo.dto.user.*;
import com.example.demo.email.util.EmailDetails;
import com.example.demo.email.util.EmailService;
import com.example.demo.model.*;
import com.example.demo.service.*;
import com.example.demo.security.JwtTokenUtil;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
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
    JwtTokenUtil jwtTokenUtil;
    //TODO Ogranicenja duzine, postojanja i  formata JSON-a lozinke; Ogranicenje da li je ID od korisnika koji salje ogranicenje
    //TODO Global ERROR handler
    @PreAuthorize("hasAuthority('ROLE_PASSENGER') || hasAuthority('ROLE_DRIVER')")
    @PutMapping(value="/{id}/changePassword",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@PathVariable(value = "id", required = true) Integer id,
                                            @RequestBody ChangePasswordDTO request){
        User user = userService.findOneById(id);
        if(user == null){
            HttpStatusMessageDTO httpStatusMessageDTO = new HttpStatusMessageDTO("User does not exist!");
            return new ResponseEntity<HttpStatusMessageDTO>(httpStatusMessageDTO, HttpStatus.NOT_FOUND);
        }
        if(!userService.passwordEncoderUser().matches(request.getOldPassword(),user.getPassword())){
            HttpStatusMessageDTO httpStatusMessageDTO = new HttpStatusMessageDTO("Current password is not matching!");
            return new ResponseEntity<HttpStatusMessageDTO>(httpStatusMessageDTO, HttpStatus.BAD_REQUEST);
        }
        user.setPassword(request.getNewPassword());
        userService.saveEncode(user);
        HttpStatusMessageDTO response = new HttpStatusMessageDTO("Password successfully changed!");
        return new ResponseEntity<HttpStatusMessageDTO>(response, HttpStatus.NO_CONTENT);
    }
    //TODO Ogranicenje da li ID pripada korisniku koji salje zahtijev
    //TODO Global ERROR handler
    //TODO Kad formiramo front dio, link do stranice za resetovanje ukljuciti u mail
    @PreAuthorize("hasAuthority('ROLE_PASSENGER') || hasAuthority('ROLE_DRIVER')")
    @GetMapping(value="/{id}/resetPassword", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendEmailForPasswordChange(@PathVariable(value = "id", required = true) Integer id) {

        User user = userService.findOneById(id);
        if(user == null){
            HttpStatusMessageDTO httpStatusMessageDTO = new HttpStatusMessageDTO("User does not exist!");
            return new ResponseEntity<HttpStatusMessageDTO>(httpStatusMessageDTO, HttpStatus.NOT_FOUND);
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
        HttpStatusMessageDTO response = new HttpStatusMessageDTO("Email with reset code has been sent!");
        return new ResponseEntity<HttpStatusMessageDTO>(response, HttpStatus.NO_CONTENT);
    }
    //TODO Ista provjjera kao i kod promjene lozinke
    //TODO Global ERROR handler
    @PreAuthorize("hasAuthority('ROLE_PASSENGER') || hasAuthority('ROLE_DRIVER')")
    @PutMapping(value="/{id}/resetPassword",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> resetPassword(@PathVariable(value = "id", required = true) Integer id,
                                            @RequestBody ResetPasswordDTO request){
        User user = userService.findOneById(id);
        if(user == null){
            HttpStatusMessageDTO httpStatusMessageDTO = new HttpStatusMessageDTO("User does not exist!");
            return new ResponseEntity<HttpStatusMessageDTO>(httpStatusMessageDTO, HttpStatus.NOT_FOUND);
        }
        String code = user.getCode().toString();
        Date expirationDate = user.getExpirationDAte();
        if(!code.equals(request.getCode()) || Date.valueOf(LocalDate.now()).after(expirationDate)){
            HttpStatusMessageDTO httpStatusMessageDTO = new HttpStatusMessageDTO("Code is expired or not correct!");
            return new ResponseEntity<HttpStatusMessageDTO>(httpStatusMessageDTO, HttpStatus.BAD_REQUEST);
        }
        user.setPassword(request.getNewPassword());
        user.setCode(null);
        user.setExpirationDAte(null);
        userService.saveEncode(user);
        HttpStatusMessageDTO response = new HttpStatusMessageDTO("Password successfully changed!");
        return new ResponseEntity<HttpStatusMessageDTO>(response, HttpStatus.NO_CONTENT);
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
    //TODO Provjera svakog parametra zahtijeva i poziv funckijej na osnovu toga
    //TODO Global ERROR handler
    //TODO Da li ID odgovara korisniku koji je poslao zahtijev
    @PreAuthorize("hasAuthority('ROLE_PASSENGER') || hasAuthority('ROLE_DRIVER')")
    @GetMapping(value = "/{id}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRides(
            @PathVariable(value = "id", required = true) Integer id,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to

    ) {
        User user = userService.findOneById(id);
        if(user == null){
            HttpStatusMessageDTO httpStatusMessageDTO = new HttpStatusMessageDTO("User does not exist!");
            return new ResponseEntity<HttpStatusMessageDTO>(httpStatusMessageDTO, HttpStatus.NOT_FOUND);
        }
        //TODO Pagination
        List<Ride> rides = null;
        if(user.getRole().equals("PASSENGER")){
            rides = passengerService.getRides(id);
        }else{
            rides = driverService.getRides(id);
        }

        MultipleDTO response = new MultipleDTO();
        List<RideDTO> rideList = new ArrayList<>();

        for(Ride ride: rides){
            RideDriverDTO driver = new RideDriverDTO(ride.getDriver());
            List<RidePassengerDTO> passengerList = new ArrayList<>();
            for(Passenger passenger: ride.getPassengers()){
                passengerList.add(new RidePassengerDTO(passenger));
            }
            RejectionDTO rejection = new RejectionDTO(ride.getRejectionMessage());
            List<RidePathDTO> pathList = new ArrayList<>();
            for(Route route: ride.getRoutes()){
                pathList.add(new RidePathDTO(route));
            }
            rideList.add(new RideDTO(ride, driver, passengerList, rejection, pathList));

        }
        response.setResults(rideList);
        response.setTotalCount(rideList.size());
        return new ResponseEntity<MultipleDTO>(response, HttpStatus.OK);
    }
    //TODO Provjera foramta e-mail-a???
    @PostMapping(value="/login",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserLoginResponseDTO> logIn(@RequestBody UserLoginRequestDTO request) throws Exception {
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(request.getEmail(),
                request.getPassword());
        Authentication auth = authenticationManager.authenticate(authReq);

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);

        User user = userService.getUser(request.getEmail());

        String token = tokenUtils.generateToken(request.getEmail(),sc.getAuthentication().getAuthorities().toArray()[0].toString(),user.getId()); // prosledjujemo email, role i id korisnika
        String refreshToken = tokenUtils.generateRefreshToken(request.getEmail(),sc.getAuthentication().getAuthorities().toArray()[0].toString(),user.getId());

        UserLoginResponseDTO jwt = new UserLoginResponseDTO();
        jwt.setAccessToken(token);
        jwt.setRefreshToken(refreshToken);

        return new ResponseEntity<UserLoginResponseDTO>(jwt, HttpStatus.OK);

    }
    //TODO Provjera ID-a korisnika koji salje zahtijev
    //TODO Global ERROR handler
    @PreAuthorize("hasAuthority('ROLE_PASSENGER') || hasAuthority('ROLE_DRIVER')")
    @GetMapping(value = "/{id}/message", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMessages(
            @PathVariable(value = "id", required = true) Integer id) {

        if(userService.findOneById(id) == null) {
            HttpStatusMessageDTO errorResponse = new HttpStatusMessageDTO("User does not exist!");
            return new ResponseEntity<HttpStatusMessageDTO>(errorResponse, HttpStatus.NOT_FOUND);
        }

        MultipleDTO response = new MultipleDTO();
        List<UserMessageResponseDTO> messageDTOS = new ArrayList<>();
        List<Message> messages = messageService.findAllBySenderId(id);
        for(Message m : messages){
            messageDTOS.add(new UserMessageResponseDTO(m));
        }
        response.setResults(messageDTOS);
        response.setTotalCount(messageDTOS.size());

        return new ResponseEntity<MultipleDTO>(response, HttpStatus.OK);
    }
    //TODO Promjena nacina dobavljanjja tokena PRINCIPAL objekat
    //TODO Global ERROR handler
    //TODO Provjera foramta JSON-a i  proslijedjenih polja
    @PreAuthorize("hasAuthority('ROLE_PASSENGER') || hasAuthority('ROLE_DRIVER')")
    @PostMapping(value = "/{id}/message", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendMessage(@PathVariable(value = "id", required = true) Integer id,
                                         @RequestBody UserMessageRequestDTO request,
                                         @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){

        String jwtToken = authorization.substring(authorization.indexOf("Bearer ") + 7);
        String mail = jwtTokenUtil.getUsernameFromToken(jwtToken);
        User sender = userService.findUserByEmail(mail);
        User receiver = userService.findOneById(id);
        Ride ride  =  rideService.findOneById(request.getRideId());
        String errorMessage = "";
        boolean errorOccurred = false;
        if(sender == null) {
            errorMessage += "User does not exist! ";
            errorOccurred = true;
        }
        if(receiver == null) {
            errorMessage += "Receiver does not exist! ";
            errorOccurred = true;
        }
        if(ride == null) {
            errorMessage += "Ride does not exist! ";
            errorOccurred = true;
        }
        if(errorOccurred){
            HttpStatusMessageDTO errorResponse = new HttpStatusMessageDTO(errorMessage);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

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


