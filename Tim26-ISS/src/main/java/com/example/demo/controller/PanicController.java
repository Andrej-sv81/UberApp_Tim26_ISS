package com.example.demo.controller;

import com.example.demo.dto.MultiplePanicResponseDTO;
import com.example.demo.dto.MultipleUsersDTO;
import com.example.demo.dto.PanicResponseDTO;
import com.example.demo.dto.UserResponseDTO;
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
@RequestMapping("/api/panic")
public class PanicController {

//    @Autowired
//    PanicService pservice;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MultiplePanicResponseDTO> getPanicNotifications(){

        return new ResponseEntity<MultiplePanicResponseDTO>(new MultiplePanicResponseDTO(), HttpStatus.OK);
    }
}
