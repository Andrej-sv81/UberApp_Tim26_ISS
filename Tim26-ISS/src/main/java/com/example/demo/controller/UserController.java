package com.example.demo.controller;


import com.example.demo.dto.*;
import com.example.demo.dto.ride.RideDTO;
import com.example.demo.dto.ride.RideDriverDTO;
import com.example.demo.dto.ride.RidePassengerDTO;
import com.example.demo.dto.ride.RidePathDTO;
import com.example.demo.dto.token.UserTokenStateDTO;
import com.example.demo.dto.user.*;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MultipleUsersDTO> getUsers(
            @RequestParam(required = false)Integer page,
            @RequestParam(required = false)Integer size){

        List<UserResponseDTO> users = new ArrayList<UserResponseDTO>();
        for(int i=0;i<10;i++){
            UserResponseDTO dummy = new UserResponseDTO();
            dummy.setId(i);
            users.add(dummy);
        }
        MultipleUsersDTO response = new MultipleUsersDTO();
        response.setResults(users);
        response.setTotalCount(users.size());
        return new ResponseEntity<MultipleUsersDTO>(response, HttpStatus.OK);
    }
    @GetMapping(value = "/{id}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MultipleRidesDTO> getRides(
            @PathVariable(value = "id", required = true) Integer id,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to

            ){
        //TODO: Napraviti ispravne mockup podatke za sve 'dubine' da bi se prikazala struktura
        MultipleRidesDTO rides = new MultipleRidesDTO();
        List<RideDTO> rideList = new ArrayList<>();

        RideDriverDTO driver = new RideDriverDTO();

        List<RidePassengerDTO> passengerList = new ArrayList<>();
        for(int i = 0; i<10;i++){
            passengerList.add(new RidePassengerDTO());
        }

        RejectionDTO rejection = new RejectionDTO();

        List<RidePathDTO> pathList= new ArrayList<>();
        for(int i = 0; i<10; i++){
            pathList.add(new RidePathDTO(new LocationDTO(), new LocationDTO()));
        }

        for(int i =0; i<10; i++){
            RideDTO dummy = new RideDTO();
            dummy.setId(i);
            dummy.setPassengers(passengerList);
            dummy.setRejection(rejection);
            dummy.setLocations(pathList);
            dummy.setDriver(driver);
            rideList.add(dummy);
        }

        rides.setResults(rideList);

        return new ResponseEntity<MultipleRidesDTO>(rides, HttpStatus.OK);
    }

    @PostMapping(value="/login",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserLoginResponseDTO> logIn(@RequestBody UserLoginRequestDTO request) throws Exception{
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getEmail());
        int expiresIn = tokenUtils.getExpiredIn();
        //TODO create refresh token
        return ResponseEntity.ok(new UserLoginResponseDTO(jwt, jwt));
    }

    @GetMapping(value="/{id}/message",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserMessagesResponseDTO> getMessages(
            @PathVariable(value = "id", required = true) Integer id){

        UserMessagesResponseDTO response = new UserMessagesResponseDTO();
        List<UserMessageResponseDTO> list = new ArrayList<>();
        for(int i = 0;i<10; i++){
            list.add(new UserMessageResponseDTO());
        }
        response.setResults(list);
        return new ResponseEntity<UserMessagesResponseDTO>(response, HttpStatus.OK);
    }

    @PostMapping(value="/{id}/message",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserMessageResponseDTO> sendMessage(@PathVariable(value = "id", required = true) Integer id,
                                                              @RequestBody UserMessageRequestDTO request) throws Exception{

        UserMessageResponseDTO response = new UserMessageResponseDTO();
        response.setId(id);
        return new ResponseEntity<UserMessageResponseDTO>(response,HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/block")
    public ResponseStatusException blockUser(@PathVariable(value="id", required = true) Integer id) throws Exception{

        //Service to block user and send back http code

        return new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id}/unblock")
    public ResponseStatusException unblockUser(@PathVariable(value="id", required = true) Integer id) throws Exception{

        //Service to unblock user and send back http code

        return new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value="/{id}/note",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserNoteResponseDTO> createNote(@PathVariable(value = "id", required = true) Integer id,
                                                              @RequestBody UserNoteRequestDTO request) throws Exception{

        UserNoteResponseDTO response = new UserNoteResponseDTO();
        response.setId(id);
        return new ResponseEntity<UserNoteResponseDTO>(response,HttpStatus.OK);
    }


    @GetMapping(value="/{id}/note",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MultipleMessagesDTO> getNotes(
            @PathVariable(value ="id", required = true) Integer id,
            @RequestParam(required = false)Integer page,
            @RequestParam(required = false)Integer size){


        MultipleMessagesDTO response = new MultipleMessagesDTO();
        List<UserNoteResponseDTO> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add(new UserNoteResponseDTO());
        }
        response.setResults(list);
        return new ResponseEntity<MultipleMessagesDTO>(response, HttpStatus.OK);
    }
}


