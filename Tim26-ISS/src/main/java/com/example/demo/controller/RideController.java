package com.example.demo.controller;

import com.example.demo.dto.DriverRideOverDTO;
import com.example.demo.dto.RejectionDTO;
import com.example.demo.dto.RideRequestDTO;
import com.example.demo.dto.RideResponseDTO;
import com.example.demo.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ride")
public class RideController {
    @Autowired
    RideService rideService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> createRide(@RequestBody RideRequestDTO ride){
        RideResponseDTO created = new RideResponseDTO();
        created.setDriver(new DriverRideOverDTO());
        created.setPassengers(ride.getPassengers());
        created.setLocations(ride.getLocations());
        created.setRejection(new RejectionDTO());
        return new ResponseEntity<RideResponseDTO>(created, HttpStatus.OK);
    }

    @GetMapping(value = "/driver/{driverId}/active",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> activeRideDriver(@PathVariable("driverId") int driverId){
        RideResponseDTO response = new RideResponseDTO();
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }
}
