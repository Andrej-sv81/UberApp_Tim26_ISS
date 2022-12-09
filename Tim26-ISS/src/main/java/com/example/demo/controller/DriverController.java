package com.example.demo.controller;

import com.example.demo.dto.DriverRequestDTO;
import com.example.demo.dto.DriverResponseDTO;
import com.example.demo.dto.MultipleDriversDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/driver")
public class DriverController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverResponseDTO> createDriver(@RequestBody DriverRequestDTO driverRequestDTO){
        DriverResponseDTO response = new DriverResponseDTO();
        response.setId(1);
        return new ResponseEntity<DriverResponseDTO>(response, HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MultipleDriversDTO> getDrivers(@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size){
        MultipleDriversDTO response = new MultipleDriversDTO();
        List<DriverResponseDTO> generetedDrivers = new ArrayList<DriverResponseDTO>();
        for (int i=0;i<10;i++){
            DriverResponseDTO dummy = new DriverResponseDTO();
            dummy.setId(i);
            generetedDrivers.add(dummy);
        }
        response.setResults(generetedDrivers);
        response.setTotalCount(generetedDrivers.size());
        return new ResponseEntity<MultipleDriversDTO>(response,HttpStatus.OK);
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverResponseDTO> getDriverInfo(@PathVariable("id") Integer id){
        DriverResponseDTO responseDTO = new DriverResponseDTO();
        responseDTO.setId(id);
        return new ResponseEntity<DriverResponseDTO>(responseDTO,HttpStatus.OK);
    }
}
