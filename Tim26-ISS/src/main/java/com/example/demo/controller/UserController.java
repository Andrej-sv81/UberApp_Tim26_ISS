package com.example.demo.controller;


import com.example.demo.dto.MultipleUsersDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MultipleUsersDTO> getUsers(@RequestParam(required = false)Integer page, @RequestParam(required = false)Integer size){
        //Collection<Passenger> passengers = passengerService.findAll();
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
}
