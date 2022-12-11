package com.example.demo.controller;

import com.example.demo.dto.VehicleLocationRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

//    @Autoired
//    VehicleService vservice;

    @PutMapping(value="/{id}/location", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseStatusException changeLocation(@PathVariable(value = "id", required = true) Integer id,
                                                  @RequestBody VehicleLocationRequestDTO request) throws Exception{

        return new ResponseStatusException(HttpStatus.NO_CONTENT);
    }
}
