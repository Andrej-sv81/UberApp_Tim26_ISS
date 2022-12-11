package com.example.demo.controller;


import com.example.demo.dto.*;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MultipleUsersDTO> getUsers(
            @RequestParam(required = false)Integer page,
            @RequestParam(required = false)Integer size){

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
    @GetMapping(value = "/{id}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MultipleRidesDTO> getRides(
            @PathVariable(value = "id", required = true) Integer id,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to

            ){
        //TODO: Napraviti ispravne mockup podatke za sve 'dubine' da bi se prikazala struktura
        MultipleRidesDTO rides = new MultipleRidesDTO();
        List<RideDTO> rideList = new ArrayList<>();

        RideDriverDTO driver = new RideDriverDTO();

        List<RidePassengerDTO> passengerList = new ArrayList<>();
        for(int i = 0; i<10;i++){
            passengerList.add(new RidePassengerDTO());
        }

        RejectionDTO rejection = new RejectionDTO();

        List<RidePathDTO> pathList= new ArrayList<>();
        for(int i = 0; i<10; i++){
            pathList.add(new RidePathDTO(new LocationDTO(), new LocationDTO()));
        }

        for(int i =0; i<10; i++){
            RideDTO dummy = new RideDTO();
            dummy.setId(i);
            dummy.setPassengers(passengerList);
            dummy.setRejection(rejection);
            dummy.setLocations(pathList);
            dummy.setDriver(driver);
            rideList.add(dummy);
        }

        rides.setResults(rideList);

        return new ResponseEntity<MultipleRidesDTO>(rides, HttpStatus.OK);
    }

    @PostMapping(value="/login",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PassengerResponseDTO> createPassenger(@RequestBody PassengerRequestDTO passenger) throws Exception{
        //Passenger savedPassenger = passengerService.create(passenger);
        PassengerResponseDTO savedPassengerDTO = new PassengerResponseDTO(1,passenger.getName(),passenger.getSurname(),passenger.getProfilePicture(),passenger.getTelephoneNumber(),passenger.getEmail(),passenger.getAddress());
        return new ResponseEntity<PassengerResponseDTO>(savedPassengerDTO,HttpStatus.CREATED);
    }
}
