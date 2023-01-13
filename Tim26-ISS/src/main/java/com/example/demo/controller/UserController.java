package com.example.demo.controller;


import com.example.demo.dto.*;
import com.example.demo.dto.ride.RideDTO;
import com.example.demo.dto.ride.RideDriverDTO;
import com.example.demo.dto.ride.RidePassengerDTO;
import com.example.demo.dto.ride.RidePathDTO;

import com.example.demo.dto.user.*;
import com.example.demo.email.util.EmailDetails;
import com.example.demo.email.util.EmailService;
import com.example.demo.model.Message;
import com.example.demo.model.Ride;
import com.example.demo.model.User;
import com.example.demo.service.MessageService;
import com.example.demo.service.RideService;
import com.example.demo.service.UserService;
//import com.example.demo.util.TokenUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/user")
public class UserController {

//    @Autowired
//    private TokenUtils tokenUtils;

//    @Autowired
//    private AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;
    @Autowired
    RideService rideService;
    @Autowired
    EmailService emailService;

    @PutMapping(value="/{id}/changePassword",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@PathVariable(value = "id", required = true) Integer id,
                                            @RequestBody ChangePasswordDTO request){
        User user = userService.findOneById(id);
        if(user == null){
            HttpStatusMessageDTO httpStatusMessageDTO = new HttpStatusMessageDTO("User does not exist!");
            return new ResponseEntity<HttpStatusMessageDTO>(httpStatusMessageDTO, HttpStatus.NOT_FOUND);
        }
        //TODO Use the password encryption in the user service so the user model has a string password
        String password = user.getPassword();
        if(!password.equals(request.getOldPassword())){
            HttpStatusMessageDTO httpStatusMessageDTO = new HttpStatusMessageDTO("Current password is not matching!");
            return new ResponseEntity<HttpStatusMessageDTO>(httpStatusMessageDTO, HttpStatus.BAD_REQUEST);
        }
        user.setPassword(request.getNewPassword());
        //TODO Password encrypt inside the service
        userService.save(user);
        HttpStatusMessageDTO response = new HttpStatusMessageDTO("Password successfully changed!");
        return new ResponseEntity<HttpStatusMessageDTO>(response, HttpStatus.NO_CONTENT);
    }

    @GetMapping(value="/{id}/resetPassword", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendEmailForPasswordChange(@PathVariable(value = "id", required = true) Integer id) {

        User user = userService.findOneById(id);
        if(user == null){
            HttpStatusMessageDTO httpStatusMessageDTO = new HttpStatusMessageDTO("User does not exist!");
            return new ResponseEntity<HttpStatusMessageDTO>(httpStatusMessageDTO, HttpStatus.NOT_FOUND);
        }
        Random rand = new Random();
        int code = rand.nextInt(9000000) + 1000000;
        //TODO add expiration to code
        user.setCode(code);
        userService.save(user);

        String body = "Hello,\n"
                + "You have requested to reset your password.\n"
                + "Use the code below to change your password:\n"
                + "\n" + code + "\n"
                + "\nIgnore this email if you do remember your password,\n "
                + "or you have not made the request.";

        String subject = "Password reset request";
        EmailDetails emailDetails = new EmailDetails(user.getEmail(),body, subject);
        emailService.sendSimpleMail(emailDetails);
        HttpStatusMessageDTO response = new HttpStatusMessageDTO("Email with reset code has been sent!");
        return new ResponseEntity<HttpStatusMessageDTO>(response, HttpStatus.NO_CONTENT);
    }

    @PutMapping(value="/{id}/resetPassword",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> resetPassword(@PathVariable(value = "id", required = true) Integer id,
                                            @RequestBody ResetPasswordDTO request){
        User user = userService.findOneById(id);
        if(user == null){
            HttpStatusMessageDTO httpStatusMessageDTO = new HttpStatusMessageDTO("User does not exist!");
            return new ResponseEntity<HttpStatusMessageDTO>(httpStatusMessageDTO, HttpStatus.NOT_FOUND);
        }
        //TODO read expiration from code and check if its still valid
        String code = user.getCode().toString();
        if(!code.equals(request.getCode())){
            HttpStatusMessageDTO httpStatusMessageDTO = new HttpStatusMessageDTO("Code is expired or not correct!");
            return new ResponseEntity<HttpStatusMessageDTO>(httpStatusMessageDTO, HttpStatus.BAD_REQUEST);
        }
        user.setPassword(request.getNewPassword());
        user.setCode(null);
        userService.save(user);
        HttpStatusMessageDTO response = new HttpStatusMessageDTO("Password successfully changed!");
        return new ResponseEntity<HttpStatusMessageDTO>(response, HttpStatus.NO_CONTENT);
    }



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

    @GetMapping(value = "/{id}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MultipleDTO> getRides(
            @PathVariable(value = "id", required = true) Integer id,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to

    ) {
        MultipleDTO rides = new MultipleDTO();
        RideDriverDTO driver = new RideDriverDTO();
        RejectionDTO rejection = new RejectionDTO();
        List<RideDTO> rideList = new ArrayList<>();
        List<RidePassengerDTO> passengerList = new ArrayList<>();
        List<RidePathDTO> pathList = new ArrayList<>();

        return new ResponseEntity<MultipleDTO>(rides, HttpStatus.OK);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserLoginResponseDTO> logIn(@RequestBody UserLoginRequestDTO request) throws Exception {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                request.getEmail(), request.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        User user = (User) authentication.getPrincipal();
//        User user = null;
//        String jwt = tokenUtils.generateToken(user.getEmail());
//        String jwt_refresh = tokenUtils.generateRefreshToken(user.getEmail());
        return ResponseEntity.ok(new UserLoginResponseDTO("", ""));
    }

    //    @PostMapping("/signup")
//    public ResponseEntity<User> addUser(@RequestBody UserRequestDTO userRequest, UriComponentsBuilder ucBuilder) {
//        User existUser = this.userService.findOneByEmail(userRequest.getEmail());
//
////       if (existUser != null) {
////            throw new ResourceConflictException(userRequest.getId(), "Username already exists");
////        }
////        User user = this.userService.save(userRequest);
////        return new ResponseEntity<>(user, HttpStatus.CREATED);
//
//        return null;


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

    @PostMapping(value = "/{id}/message", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendMessage(@PathVariable(value = "id", required = true) Integer id,
                                                              @RequestBody UserMessageRequestDTO request) throws Exception {

        //TODO Get the sender from the JWT token provided in the request
        User sender = userService.findOneById(1);
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
    @PutMapping(value = "/{id}/block")
    public ResponseStatusException blockUser(@PathVariable(value = "id", required = true) Integer id) throws Exception {
        return new ResponseStatusException(HttpStatus.NO_CONTENT);
    }
    //ADMIN Endpoint
    @PutMapping(value = "/{id}/unblock")
    public ResponseStatusException unblockUser(@PathVariable(value = "id", required = true) Integer id) throws Exception {
        return new ResponseStatusException(HttpStatus.NO_CONTENT);
    }
    //ADMIN Endpoint
    @PostMapping(value = "/{id}/note", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserNoteResponseDTO> createNote(@PathVariable(value = "id", required = true) Integer id,
                                                          @RequestBody UserNoteRequestDTO request) throws Exception {
        UserNoteResponseDTO response = new UserNoteResponseDTO();
        response.setId(id);
        return new ResponseEntity<UserNoteResponseDTO>(response, HttpStatus.OK);
    }
    //ADMIN Endpoint
    @GetMapping(value = "/{id}/note", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MultipleMessagesDTO> getNotes(
            @PathVariable(value = "id", required = true) Integer id,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        MultipleMessagesDTO response = new MultipleMessagesDTO();
        List<UserNoteResponseDTO> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new UserNoteResponseDTO());
        }
        response.setResults(list);
        return new ResponseEntity<MultipleMessagesDTO>(response, HttpStatus.OK);
    }
}


