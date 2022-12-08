package com.example.demo.controller;

import com.example.demo.dto.PassengerRequestDTO;
import com.example.demo.dto.PassengerResponseDTO;
import com.example.demo.model.Passenger;
import com.example.demo.service.PassengerService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/api/passenger")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PassengerResponseDTO> createPassenger(@RequestBody PassengerRequestDTO passenger) throws Exception{
        //Passenger savedPassenger = passengerService.create(passenger);
        PassengerResponseDTO savedPassengerDTO = new PassengerResponseDTO(1,passenger.getName(),passenger.getSurname(),passenger.getProfilePicture(),passenger.getTelephoneNumber(),passenger.getEmail(),passenger.getAddress());
        return new ResponseEntity<PassengerResponseDTO>(savedPassengerDTO,HttpStatus.CREATED);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<PassengerResponseDTO>> getPassengers(){
        //Collection<Passenger> passengers = passengerService.findAll();
        Collection<PassengerResponseDTO> passengers = new ArrayList<PassengerResponseDTO>();
        for(int i=0;i<10;i++){
            PassengerResponseDTO dummy = new PassengerResponseDTO();
            dummy.setId(i);
            passengers.add(dummy);
        }
        return new ResponseEntity<Collection<PassengerResponseDTO>>(passengers, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PassengerResponseDTO> getPassenger(@PathVariable("id") int id){
//        Passenger passenger = passengerService.findOne(id);
//        if(passenger == null){
//            return new ResponseEntity<Passenger>(HttpStatus.NOT_FOUND);
//        }
        PassengerResponseDTO response = new PassengerResponseDTO();
        response.setId(id);
        return new ResponseEntity<PassengerResponseDTO>(response,HttpStatus.OK);
    }

    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PassengerResponseDTO> updatePassenger(@RequestBody PassengerRequestDTO passenger, @PathVariable int id) throws Exception{
        PassengerResponseDTO passengerForUpdate = new PassengerResponseDTO();
        //TODO ZAVRSI APDEJTOVANJE PUTNIKA
        passengerForUpdate.setName("APDEJTOVANKO");
        passengerForUpdate.setSurname("KANTA");
        passengerForUpdate.setEmail(passenger.getEmail());
        passengerForUpdate.setAddress(passenger.getAddress());
        passengerForUpdate.setProfilePicture(passenger.getProfilePicture());
        passengerForUpdate.setTelephoneNumber(passenger.getTelephoneNumber());
        return new ResponseEntity<>(passengerForUpdate,HttpStatus.OK);
    }



    @GetMapping(value = "/activate/{activationId}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> activateAccount(@PathVariable int activationId){
//        Passenger found = passengerService.findOne(activationId);
//        if (found == null){
//            return new ResponseEntity<String>("Account not found.",HttpStatus.NOT_FOUND);
//        }
        return new ResponseEntity<String>("ACTIVATED",HttpStatus.OK);
    }
}
