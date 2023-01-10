package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.dto.driver.DriverRideOverDTO;
import com.example.demo.dto.ride.RidePanicResponseDTO;
import com.example.demo.dto.ride.RideRequestDTO;
import com.example.demo.dto.ride.RideResponseDTO;
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

    @GetMapping(value = "/passenger/{passengerId}/active",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> activeRidePassenger(@PathVariable("passengerId") int passengerId){
        RideResponseDTO response = new RideResponseDTO();
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }

    @GetMapping(value = "{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> getDetailsRide(@PathVariable("id") int id){
        RideResponseDTO response = new RideResponseDTO();
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }

    @PutMapping(value = "{id}/withdraw",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> cancelRide(@PathVariable("id") int id){
        RideResponseDTO response = new RideResponseDTO();
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/panic",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RidePanicResponseDTO> panicProcedure(@PathVariable("id")int id, @RequestBody ExplanationDTO panic){
        RidePanicResponseDTO response = new RidePanicResponseDTO();
        return new ResponseEntity<RidePanicResponseDTO>(response,HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/accept",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> acceptRide(@PathVariable("id") int id){
        RideResponseDTO response = new RideResponseDTO();
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/end",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> endRide(@PathVariable("id") int id){
        RideResponseDTO response = new RideResponseDTO();
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/cancel",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> cancelWithExplanation(@PathVariable("id")int id,@RequestBody ExplanationDTO explanation){
        RideResponseDTO response = new RideResponseDTO();
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }
}
