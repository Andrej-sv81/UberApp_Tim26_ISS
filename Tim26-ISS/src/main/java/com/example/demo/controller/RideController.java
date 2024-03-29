package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.dto.driver.DriverRideOverDTO;
import com.example.demo.dto.passenger.PassengerRideOverDTO;
import com.example.demo.dto.ride.*;
import com.example.demo.exceptions.*;
import com.example.demo.model.*;
import com.example.demo.repository.DriverRepository;
import com.example.demo.service.*;
import com.example.demo.service.AssignRideService;
import com.example.demo.service.PassengerService;
import com.example.demo.service.RideService;
import com.example.demo.service.VehicleTypeService;

import com.example.demo.util.cost.EstimatedCost;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@RestController
@Validated
@RequestMapping("/api/ride")
public class RideController {
    @Autowired
    RideService rideService;
    @Autowired
    VehicleTypeService vehicleTypeService;
    @Autowired
    PassengerService passengerService;
    @Autowired
    RejectionMessageService rejectionMessageService;
    @Autowired
    RouteService routeService;
    @Autowired
    UserService userService;
    @Autowired
    DriverService driverService;
    @Autowired
    PanicService panicService;
    @Autowired
    FavoriteRidesService favoriteRidesService;
    @Autowired
    AssignRideService assignRideService;
    @Autowired
    private DriverRepository driverRepository;

    public Map<Integer,List<Driver>> rejectedRides = new HashMap<Integer,List<Driver>>();


    @PreAuthorize("hasAuthority('ROLE_PASSENGER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> createRide(@Valid @RequestBody RideRequestDTO ride,
                                                      Principal userPrincipal) throws JsonProcessingException {

        RideResponseDTO response = rideService.createRide(ride,userPrincipal);
        return new ResponseEntity<RideResponseDTO>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @GetMapping(value = "/driver/{driverId}/active",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> activeRideDriver(@PathVariable(value = "driverId", required = true) @NotNull Integer driverId,
                                                            Principal userPrincipal){
        User user = userService.findUserByEmail(userPrincipal.getName());
        if(user.getId() != driverId){
            throw new UserIdNotMatchingException();
        }
        RideResponseDTO response = rideService.findActiveRideForDriver(driverId);
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_PASSENGER')")
    @GetMapping(value = "/passenger/{passengerId}/active",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> activeRidePassenger(@PathVariable(value = "passengerId", required = true) @NotNull Integer passengerId,
                                                               Principal userPrincipal){
        User user = userService.findUserByEmail(userPrincipal.getName());
        if(user.getId() != passengerId){
            throw new UserIdNotMatchingException();
        }
        RideResponseDTO response = rideService.findActiveRideForPassenger(passengerId);
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_PASSENGER') || hasAuthority('ROLE_DRIVER')")
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> getDetailsRide(@PathVariable(value = "id", required = true) @NotNull Integer id){
        RideResponseDTO response = rideService.rideDetails(id);
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_PASSENGER')")
    @PutMapping(value = "{id}/withdraw",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> cancelRide(@PathVariable(value = "id", required = true) @NotNull Integer id,
                                                      Principal userPrincipal){
        RideResponseDTO response = rideService.cancelRidePassenger(id,userPrincipal);
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }


    // TODO PANIC PROCEDURE!!!!!!!!!!!!!!!!!
    @PreAuthorize("hasAuthority('ROLE_PASSENGER') || hasAuthority('ROLE_DRIVER')")
    @PutMapping(value = "/{id}/panic",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RidePanicResponseDTO> panicProcedure(@PathVariable(value = "id", required = true) @NotNull Integer id,
                                                               @Valid @RequestBody ExplanationDTO explanation,
                                                               Principal userPrincipal){
        User user = userService.findUserByEmail(userPrincipal.getName());
        Ride ride = rideService.findOneById(id);
        Date date = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());;

        Panic panic = new Panic(user, ride, date, explanation.getReason());
        panicService.save(panic);
        RidePanicResponseDTO response = new RidePanicResponseDTO();
        return new ResponseEntity<RidePanicResponseDTO>(response,HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @PutMapping(value = "/{id}/start",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> startRide(@PathVariable("id") @NotNull Integer id,
                                                     Principal userPrincipal){
        RideResponseDTO response = rideService.startRide(id,userPrincipal);
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @PutMapping(value = "/{id}/accept",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> acceptRide(@PathVariable("id") @NotNull Integer id,
                                                      Principal userPrincipal){
        RideResponseDTO response = rideService.acceptRide(id,userPrincipal);
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @PutMapping(value = "/{id}/end",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> endRide(@PathVariable("id") @NotNull Integer id,
                                                   Principal userPrincipal){
        RideResponseDTO response = rideService.endRide(id,userPrincipal);
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @PutMapping(value = "/{id}/cancel",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> cancelWithExplanation(@PathVariable("id")@NotNull Integer id,
                                                                  @RequestBody ExplanationDTO explanation,
                                                                 Principal userPrincipal){
        RideResponseDTO response = rideService.decline(id,explanation,userPrincipal);
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_PASSENGER')")
    @PostMapping(value = "/favorites",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FavoritesResponseDTO> createFavoriteLocations(@Valid @RequestBody FavoritesRequestDTO request,
                                                                        Principal userPrincipal){
        User user = userService.findUserByEmail(userPrincipal.getName());
        int count  = favoriteRidesService.getCount(user.getId());
        if(count >= 10){
            throw new NumberOfFavoriteRidesException();
        }

        List<Passenger> ridePassengers = new ArrayList<Passenger>();
        for(RidePassengerDTO p: request.getPassengers()){
            ridePassengers.add(passengerService.findPassenger(p.getId()));
        }

        List<Route> rideRoutes = new ArrayList<Route>();
        for(RouteDTO r: request.getLocations()){
            rideRoutes.add(new Route(request.getLocations().get(0)));
        }

        VehicleType type  = vehicleTypeService.findOneByName(VehicleTypeEnum.getType(request.getVehicleType()));
        FavoriteRide ride = new FavoriteRide(request, ridePassengers, rideRoutes, type);
        favoriteRidesService.save(ride);
        FavoritesResponseDTO response = new FavoritesResponseDTO(ride, passengerService, routeService);
        return new ResponseEntity<FavoritesResponseDTO>(response,HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_PASSENGER')")
    @GetMapping(value = "/favorites",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FavoritesResponseDTO>> getFavoriteLocations(Principal userPrincipal){
        User user = userService.findUserByEmail(userPrincipal.getName());
        List<FavoriteRide> rides = favoriteRidesService.getRidesOfUser(user.getId());
        List<FavoritesResponseDTO> response = new ArrayList<FavoritesResponseDTO>();
        for(FavoriteRide r: rides){
            response.add(new FavoritesResponseDTO(r,passengerService, routeService));
        }
        return new ResponseEntity<List<FavoritesResponseDTO>>(response,HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_PASSENGER')")
    @DeleteMapping(value = "/favorites/{id}")
    public ResponseEntity<?> deleteFavoriteRide(@PathVariable("id")@NotNull Integer id,
                                                Principal userPrincipal){
        FavoriteRide ride = favoriteRidesService.findOneById(id);
        Integer[] user_ids  =  favoriteRidesService.getUserOfRide(id);
        User user = userService.findUserByEmail(userPrincipal.getName());
        boolean found = false;
        for(Integer user_id : user_ids){
            if(user.getId() == user_id){
                found = true;
            }
        }
        if(!found){
            throw new UserIdNotMatchingException();
        }
        favoriteRidesService.delete(ride);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value="/simulate/{id}")
    public ResponseEntity<String> simulateRide(@PathVariable Integer id){
        rideService.simulate(id);
        return new ResponseEntity<>("Simulating ...",HttpStatus.OK);
    }


}
