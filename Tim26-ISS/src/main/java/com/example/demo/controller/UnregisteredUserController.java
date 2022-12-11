package com.example.demo.controller;

import com.example.demo.dto.UnregisteredRequestDTO;
import com.example.demo.dto.UnregisteredResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/unregisteredUser")
public class UnregisteredUserController {

    @PostMapping( value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UnregisteredResponseDTO>  rideAssumption(@RequestBody UnregisteredRequestDTO request) throws Exception{

        return  new ResponseEntity<UnregisteredResponseDTO>(new UnregisteredResponseDTO(), HttpStatus.OK);
    }
}
