package com.example.demo.controller;

import com.example.demo.dto.MultipleDTO;
import com.example.demo.dto.MultiplePassengersDTO;
import com.example.demo.dto.MultipleRidesDTO;
import com.example.demo.dto.passenger.PassengerRequestDTO;
import com.example.demo.dto.passenger.PassengerResponseDTO;
import com.example.demo.dto.user.UserResponseDTO;
import com.example.demo.model.Passenger;
import com.example.demo.model.User;
import com.example.demo.repository.PassengerRepository;
import com.example.demo.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/passenger")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;
    @Autowired
    private PassengerRepository passengerRepository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PassengerResponseDTO> createPassenger(@RequestBody PassengerRequestDTO passenger) throws Exception{
        PassengerResponseDTO saved = passengerService.insert(new Passenger(passenger));
        return new ResponseEntity<PassengerResponseDTO>(saved,HttpStatus.OK);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_PASSENGER')")
    public ResponseEntity<MultipleDTO> getPassengers(@RequestParam Integer page, @RequestParam Integer size){
        Pageable pageable = PageRequest.of(page,size);
        List<User> passengers = passengerRepository.findAll(pageable).getContent();
        List<UserResponseDTO> responseDTOS = UserResponseDTO.makeMultipleResponse(passengers);
        return new ResponseEntity<MultipleDTO>(new MultipleDTO(responseDTOS.size(),responseDTOS),HttpStatus.OK);
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_PASSENGER')")
    public ResponseEntity<PassengerResponseDTO> getPassenger(@PathVariable("id") int id){
        Optional<User> found = passengerRepository.findById(id);
        if(found.isEmpty()){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        PassengerResponseDTO response = new PassengerResponseDTO((Passenger) found.get());
        return new ResponseEntity<PassengerResponseDTO>(response,HttpStatus.OK);
    }

    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_PASSENGER')")
    public ResponseEntity<PassengerResponseDTO> updatePassenger(@RequestBody PassengerRequestDTO passenger, @PathVariable Integer id) throws Exception{
        PassengerResponseDTO updated = passengerService.update(passenger,id);
        return new ResponseEntity<>(updated,HttpStatus.OK);
    }

    @GetMapping(value = "/activate/{activationId}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> activateAccount(@PathVariable Integer activationId){
        Optional<User> found = passengerRepository.findById(activationId);
        if (found.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/ride",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_PASSENGER')")
    public ResponseEntity<MultipleRidesDTO> getAllRides(@PathVariable("id") int id,
                                                        @RequestParam(required = false) int page,
                                                        @RequestParam(required = false) int size,
                                                        @RequestParam(required = false) String sort,
                                                        @RequestParam(required = false) String from,
                                                        @RequestParam(required = false) String to){
        MultipleRidesDTO response = new MultipleRidesDTO();
        return new ResponseEntity<MultipleRidesDTO>(response,HttpStatus.OK);
    }
}
