package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.dto.driver.DriverRideOverDTO;
import com.example.demo.dto.passenger.PassengerRideOverDTO;
import com.example.demo.dto.ride.RidePanicResponseDTO;
import com.example.demo.dto.ride.RideRequestDTO;
import com.example.demo.dto.ride.RideResponseDTO;
import com.example.demo.model.*;
import com.example.demo.service.PassengerService;
import com.example.demo.service.RideService;
import com.example.demo.service.VehicleTypeService;
import com.example.demo.util.cost.EstimatedCost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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

    @PreAuthorize("hasAuthority('ROLE_PASSENGER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> createRide(@Valid @RequestBody RideRequestDTO ride,
                                                      Principal userPrincipal){
        Passenger passenger = passengerService.findPassengerByEmail(userPrincipal.getName());
        //TODO checks passenger rides for already pending ride

        VehicleType vehicleType = vehicleTypeService.findOneByName(
                                VehicleTypeEnum.getType(ride.getVehicleType()));

        double[] timeCostAndDistance = caclculateTimeAndCost(ride.getLocations().get(0), vehicleType);
        int estimated_time = (int)timeCostAndDistance[0];
        int total_cost = (int)timeCostAndDistance[1];
        double distance = timeCostAndDistance[2];

        List<Passenger> passengerList = new ArrayList<Passenger>();
        for(PassengerRideOverDTO p: ride.getPassengers()){
            Passenger newP = passengerService.findPassengerByEmail(p.getEmail());
            //newP.getRoutes().size(); //INVOKE LAZY LOAD ???
            passengerList.add(newP);
        }

        List<Route> routeList = new ArrayList<Route>();
        Location start = new Location(ride.getLocations().get(0).getDeparture());
        Location end = new Location(ride.getLocations().get(0).getDestination());
        Route route = new Route(start, end, distance);
        routeList.add(route);

        List<Review> reviews = new ArrayList<Review>();

        Ride newRide = new Ride(null, null, total_cost, null,
                                passengerList, routeList, estimated_time, reviews,
                                RideState.PENDING, null, false,
                                ride.isBabyTransport(), ride.isPetTransport(), vehicleType);

        rideService.save(newRide);
        RideResponseDTO response = new RideResponseDTO(newRide);
        return new ResponseEntity<RideResponseDTO>(response, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @GetMapping(value = "/driver/{driverId}/active",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> activeRideDriver(@PathVariable(value = "driverId", required = true) @NotNull Integer driverId){
        //TODO Return active ride details for the driver,  ACTIVE status RIDE; 404
        RideResponseDTO response = new RideResponseDTO();
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_PASSENGER')")
    @GetMapping(value = "/passenger/{passengerId}/active",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> activeRidePassenger(@PathVariable(value = "passengerId", required = true) @NotNull Integer passengerId){
        //TODO Return active ride details for the passenger,  ACTIVE status RIDE; 404
        RideResponseDTO response = new RideResponseDTO();
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_PASSENGER') || hasAuthority('ROLE_DRIVER')")
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> getDetailsRide(@PathVariable(value = "id", required = true) @NotNull Integer id){
        //TODO same as above return details; 404; 400
        RideResponseDTO response = new RideResponseDTO();
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_PASSENGER')")
    @PutMapping(value = "{id}/withdraw",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> cancelRide(@PathVariable(value = "id", required = true) @NotNull Integer id){
        //TODO cancel if not pending started 400; 404
        RideResponseDTO response = new RideResponseDTO();
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_PASSENGER') || hasAuthority('ROLE_DRIVER')")
    @PutMapping(value = "/{id}/panic",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RidePanicResponseDTO> panicProcedure(@PathVariable(value = "id", required = true) @NotNull Integer id,
                                                               @Valid @RequestBody ExplanationDTO panic){
        //TODO 404
        RidePanicResponseDTO response = new RidePanicResponseDTO();
        return new ResponseEntity<RidePanicResponseDTO>(response,HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @PutMapping(value = "/{id}/start",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> startRide(@PathVariable("id") @NotNull Integer id){
        //TODO 400 404
        RideResponseDTO response = new RideResponseDTO();
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @PutMapping(value = "/{id}/accept",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> acceptRide(@PathVariable("id") @NotNull Integer id){

        //TODO 400 404
        RideResponseDTO response = new RideResponseDTO();
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @PutMapping(value = "/{id}/end",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> endRide(@PathVariable("id") @NotNull Integer id){
        //TODO 400 404
        RideResponseDTO response = new RideResponseDTO();
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @PutMapping(value = "/{id}/cancel",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> cancelWithExplanation(@PathVariable("id")@NotNull Integer id,
                                                                 @Valid @RequestBody ExplanationDTO explanation){
        //TODO 400 404
        RideResponseDTO response = new RideResponseDTO();
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_PASSENGER')")
    @PostMapping(value = "/favorites",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> createFavoriteLocations(@Valid @RequestBody ExplanationDTO explanation){
        //TODO 400 NEW DTO
        RideResponseDTO response = new RideResponseDTO();
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_PASSENGER')")
    @GetMapping(value = "/favorites",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> getFavoriteLocations(){
        //TODO  NEW DTO
        RideResponseDTO response = new RideResponseDTO();
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_PASSENGER')")
    @DeleteMapping(value = "/favorites/{id}")
    public ResponseEntity<RideResponseDTO> deleteFavoriteRide(@PathVariable("id")@NotNull Integer id){
        //TODO  204, 404
        RideResponseDTO response = new RideResponseDTO();
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }

    public double[] caclculateTimeAndCost(RouteDTO route, VehicleType type){
        LocationDTO start = route.getDeparture();
        LocationDTO finish = route.getDestination();
        double distance = EstimatedCost.calculateDistance(start.getLatitude(), start.getLongitude(),
                                                          finish.getLatitude(), finish.getLongitude());

        double price = type.getPrice() + distance * 120;
        double time = distance/80 * 60;

        return new double[]{time, price, distance};
    }
}
