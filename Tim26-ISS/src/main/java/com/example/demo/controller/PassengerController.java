package com.example.demo.controller;

import com.example.demo.dto.MultiplePassengersDTO;
import com.example.demo.dto.MultipleRidesDTO;
import com.example.demo.dto.passenger.PassengerRequestDTO;
import com.example.demo.dto.passenger.PassengerResponseDTO;
import com.example.demo.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/passenger")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PassengerResponseDTO> createPassenger(@RequestBody PassengerRequestDTO passenger) throws Exception{
        //Passenger savedPassenger = passengerService.create(passenger);
        PassengerResponseDTO savedPassengerDTO = new PassengerResponseDTO(1,passenger.getName(),passenger.getSurname(),passenger.getProfilePicture(),passenger.getTelephoneNumber(),passenger.getEmail(),passenger.getAddress());
        return new ResponseEntity<PassengerResponseDTO>(savedPassengerDTO, HttpStatus.OK);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MultiplePassengersDTO> getPassengers(@RequestParam(required = false)Integer page, @RequestParam(required = false)Integer size){
        //Collection<Passenger> passengers = passengerService.findAll();
        List<PassengerResponseDTO> passengers = new ArrayList<PassengerResponseDTO>();
        for(int i=0;i<10;i++){
            PassengerResponseDTO dummy = new PassengerResponseDTO();
            dummy.setId(i);
            passengers.add(dummy);
        }
        MultiplePassengersDTO response = new MultiplePassengersDTO();
        response.setResults(passengers);
        response.setTotalCount(passengers.size());
        return new ResponseEntity<MultiplePassengersDTO>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_PASSENGER')")
    public ResponseEntity<PassengerResponseDTO> getPassenger(@PathVariable("id") int id){
        PassengerResponseDTO response = new PassengerResponseDTO();
        response.setId(id);
        return new ResponseEntity<PassengerResponseDTO>(response,HttpStatus.OK);
    }

    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PassengerResponseDTO> updatePassenger(@RequestBody PassengerRequestDTO passenger, @PathVariable int id) throws Exception{
        PassengerResponseDTO passengerForUpdate = new PassengerResponseDTO();
        passengerForUpdate.setName("APDEJTOVANKO");
        passengerForUpdate.setSurname("KANTA");
        passengerForUpdate.setEmail(passenger.getEmail());
        passengerForUpdate.setAddress(passenger.getAddress());
        passengerForUpdate.setProfilePicture(passenger.getProfilePicture());
        passengerForUpdate.setTelephoneNumber(passenger.getTelephoneNumber());
        return new ResponseEntity<>(passengerForUpdate,HttpStatus.OK);
    }

    @GetMapping(value = "/activate/{activationId}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> activateAccount(@PathVariable int activationId){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/ride",produces = MediaType.APPLICATION_JSON_VALUE)
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
