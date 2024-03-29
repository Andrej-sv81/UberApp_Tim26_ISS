package com.example.demo.controller;

import com.example.demo.dto.HttpStatusMessageDTO;
import com.example.demo.dto.MultipleDTO;
import com.example.demo.dto.MultiplePassengersDTO;
import com.example.demo.dto.MultipleRidesDTO;
import com.example.demo.dto.passenger.PassengerRequestDTO;
import com.example.demo.dto.passenger.PassengerResponseDTO;
import com.example.demo.dto.passenger.PassengerUpdateRequestDTO;
import com.example.demo.dto.user.UserResponseDTO;
import com.example.demo.exceptions.ActivationExpiredException;
import com.example.demo.exceptions.ForbiddenDataUpdateException;
import com.example.demo.exceptions.UserDoesNotExistException;
import com.example.demo.exceptions.UserIdNotMatchingException;
import com.example.demo.model.Passenger;
import com.example.demo.model.User;
import com.example.demo.model.UserActivation;
import com.example.demo.repository.PassengerRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenUtil;
import com.example.demo.service.PassengerService;
import com.example.demo.service.UserActivationService;
import com.example.demo.service.UserService;
import com.example.demo.util.email.EmailDetails;
import com.example.demo.util.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Validated
@RestController
@RequestMapping("/api/passenger")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;
    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    UserActivationService userActivationService;
    @Autowired
    EmailService emailService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPassenger(@RequestBody PassengerRequestDTO passenger) throws Exception{
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        passenger.setPassword(encoder.encode(passenger.getPassword()));

        PassengerResponseDTO saved = passengerService.insert(new Passenger(passenger));
        if (saved == null){
            HttpStatusMessageDTO response = new HttpStatusMessageDTO("User already exists.");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        User user = userService.findOneById(saved.getId());
        Date dateCreated = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        Date dateToExpire = Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
        UserActivation activation = new UserActivation(user, dateCreated, dateToExpire );
        userActivationService.save(activation);

        String url = "http://localhost:4200/activate";

        String body = "Hello,\n"
                + "Before you can use your GariGO account\n"
                + "you need to activate it using the activation code bellow.\n"
                + "\n" + activation.getId() + "\n"
                + "\nThe code will expire in 1 day.\n\n "
                + "\nEnter the activation code at the following page: \n"
                + "\n  " + url;

        String subject = "Account activation request";
        EmailDetails emailDetails = new EmailDetails(user.getEmail(),body, subject);
        emailService.sendSimpleMail(emailDetails);
        return new ResponseEntity<PassengerResponseDTO>(saved,HttpStatus.OK);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_PASSENGER')")
    public ResponseEntity<MultipleDTO> getPassengers(@RequestParam Integer page, @RequestParam Integer size){
        Pageable pageable = PageRequest.of(page,size);
        List<User> passengers = passengerRepository.findAll(pageable).getContent();
        List<UserResponseDTO> responseDTOS = UserResponseDTO.makeMultipleResponse(passengers);
        return new ResponseEntity<>(new MultipleDTO(responseDTOS.size(),responseDTOS),HttpStatus.OK);
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_PASSENGER')")
    public ResponseEntity<?> getPassenger(@PathVariable("id") Integer id, Principal userPrincipal){
        Optional<User> found = userRepository.findById(id);
        if(found.isEmpty()){
            throw new UserDoesNotExistException();
        }
        if(!userPrincipal.getName().equals(found.get().getEmail())){
            throw new UserIdNotMatchingException();
        }
        PassengerResponseDTO response = new PassengerResponseDTO((Passenger) found.get());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_PASSENGER')")
    public ResponseEntity<?> updatePassenger(@Valid @RequestBody PassengerUpdateRequestDTO passenger, @PathVariable Integer id, Principal userPrincipal) throws Exception{
        Optional<User> check = passengerRepository.findById(id);
        if (check.isEmpty()){
            throw new UserDoesNotExistException();
        }
        if(!userPrincipal.getName().equals(check.get().getEmail())){
            throw new UserIdNotMatchingException();
        }

        PassengerResponseDTO updated = passengerService.update(passenger,id);
        return new ResponseEntity<>(updated,HttpStatus.OK);
    }

    @GetMapping(value = "/activate/{activationId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activateAccount(@PathVariable Integer activationId){
        UserActivation activation = userActivationService.findById(activationId);
        Optional<User> found = passengerRepository.findById(activation.getUser().getId());
        if (found.isEmpty()){
            throw new UserDoesNotExistException();
        }
        if(activation.getLifeTime().before(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))){
            throw new ActivationExpiredException();
        }
        User user = found.get();
        user.setActive(true);
        userService.save(user);
        userActivationService.delete(activation);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/ride",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_PASSENGER')")
    public ResponseEntity<MultipleRidesDTO> getAllRides(@PathVariable("id") int id,
                                                        @RequestParam(required = false) int page,
                                                        @RequestParam(required = false) int size,
                                                        @RequestParam(required = false) String sort,
                                                        @RequestParam(required = false) String from,
                                                        @RequestParam(required = false) String to,Principal userPrincipal){
        User user = userService.findOneById(id);
        if(!userPrincipal.getName().equals(user.getEmail())){
            throw new UserIdNotMatchingException();
        }
        MultipleRidesDTO response = new MultipleRidesDTO();
        Pageable pageable = PageRequest.of(page,size, Sort.by(sort));

        return new ResponseEntity<MultipleRidesDTO>(response,HttpStatus.OK);

    }
}
