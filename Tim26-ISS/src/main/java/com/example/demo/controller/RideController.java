package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.dto.driver.DriverRideOverDTO;
import com.example.demo.dto.passenger.PassengerRideOverDTO;
import com.example.demo.dto.ride.RidePanicResponseDTO;
import com.example.demo.dto.ride.RidePassengerDTO;
import com.example.demo.dto.ride.RideRequestDTO;
import com.example.demo.dto.ride.RideResponseDTO;
import com.example.demo.exceptions.*;
import com.example.demo.model.*;
import com.example.demo.service.*;
import com.example.demo.service.AssignRideService;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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

    @PreAuthorize("hasAuthority('ROLE_PASSENGER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> createRide(@Valid @RequestBody RideRequestDTO ride,
                                                      Principal userPrincipal){
        Passenger passenger = passengerService.findPassengerByEmail(userPrincipal.getName());
        List<Ride> pendingRides = rideService.findPendingRides(passenger.getId()); //checks for pending rides

        VehicleType vehicleType = vehicleTypeService.findOneByName(
                                VehicleTypeEnum.getType(ride.getVehicleType()));
        double[] timeCostAndDistance = caclculateTimeAndCost(ride.getLocations().get(0), vehicleType);
        int estimated_time = (int)timeCostAndDistance[0];
        int total_cost = (int)timeCostAndDistance[1];
        double distance = timeCostAndDistance[2];

        List<Passenger> passengerList = new ArrayList<Passenger>();
        for(PassengerRideOverDTO p: ride.getPassengers()){
            Passenger newP = passengerService.findPassengerByEmail(p.getEmail());
            passengerList.add(newP);
        }

        List<Route> routeList = new ArrayList<Route>();
        Location start = new Location(ride.getLocations().get(0).getDeparture());
        Location end = new Location(ride.getLocations().get(0).getDestination());
        Route route = new Route(start, end, distance);
        routeList.add(route);

        List<Review> reviews = new ArrayList<Review>();

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date scheduledTime;
        try {
            if(!ride.getScheduledTime().equals("")) {
                scheduledTime = formatter.parse(ride.getScheduledTime());
            }else {
                scheduledTime = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault())
                                .toInstant());;
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Ride newRide = new Ride(null, null, total_cost, null,
                                passengerList, routeList, estimated_time, reviews,
                                RideState.PENDING, null, false,
                                ride.isBabyTransport(), ride.isPetTransport(), vehicleType, scheduledTime);
        //TODO assign ride to driver import assing service
        Driver proba = assignRideService.assignDriver(newRide);
        System.out.println(proba);
        rideService.save(newRide);

        for(Passenger p: passengerList){ // Petlja za bidirekciono cuvanje, jer ne mozemo cascadeAll zbog Dethached entity
            List<Ride> rides = p.getRides();
            rides.add(newRide);
            p.setRides(rides);
            passengerService.insert(p);
        }
        RideResponseDTO response = new RideResponseDTO(newRide, ride.getPassengers(), ride.getLocations());
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
        Ride ride = rideService.findActiveRideForDriver(driverId);
        RideResponseDTO response = new RideResponseDTO(ride, rejectionMessageService, passengerService, routeService);
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
        Ride ride = rideService.findActiveRideForPassenger(passengerId);
        RideResponseDTO response = new RideResponseDTO(ride, rejectionMessageService, passengerService, routeService);
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_PASSENGER') || hasAuthority('ROLE_DRIVER')")
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> getDetailsRide(@PathVariable(value = "id", required = true) @NotNull Integer id){

        Ride ride = rideService.findOneById(id);
        RideResponseDTO response = new RideResponseDTO(ride, rejectionMessageService, passengerService, routeService);
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_PASSENGER')")
    @PutMapping(value = "{id}/withdraw",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> cancelRide(@PathVariable(value = "id", required = true) @NotNull Integer id,
                                                      Principal userPrincipal){
        Passenger passenger = passengerService.findPassengerByEmail(userPrincipal.getName());
        List<Passenger> passengerList = passengerService.getPassengersOfRide(id);
        boolean contains = false;
        for(Passenger p: passengerList){
            if(p.getId() == passenger.getId()){
                contains = true;
            }
        }
        if(!contains){
            throw new UserIdNotMatchingException();
        }

        Ride ride = rideService.findOneById(id);
        if(ride.getRideState() == RideState.PENDING || ride.getRideState() == RideState.STARTED){
            ride.setRideState(RideState.CANCELED);
        }else{
            throw new CannotCancelRideException();
        }

        rideService.save(ride);
        RideResponseDTO response = new RideResponseDTO(ride, rejectionMessageService, passengerService, routeService);
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }
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
        RidePanicResponseDTO response = new RidePanicResponseDTO(panic, rejectionMessageService, routeService, passengerService);
        return new ResponseEntity<RidePanicResponseDTO>(response,HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @PutMapping(value = "/{id}/start",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> startRide(@PathVariable("id") @NotNull Integer id,
                                                     Principal userPrincipal){
        Driver rideDriver = driverService.getDriverOfRide(id);
        if(!rideDriver.getEmail().equals(userPrincipal.getName())){
            throw new UserIdNotMatchingException();
        }

        Ride ride = rideService.findOneById(id);
        if(ride.getRideState() == RideState.ACCEPTED){
            ride.setRideState(RideState.STARTED);
            ride.setStartTime(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        }else{
            throw new CannotStartRideException();
        }

        rideService.save(ride);
        RideResponseDTO response = new RideResponseDTO(ride, rejectionMessageService, passengerService, routeService);
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @PutMapping(value = "/{id}/accept",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> acceptRide(@PathVariable("id") @NotNull Integer id,
                                                      Principal userPrincipal){
        Driver driver = driverService.findByEmail(userPrincipal.getName());
        Ride ride = rideService.findOneById(id);
        if(ride.getRideState() == RideState.PENDING){
            ride.setRideState(RideState.ACCEPTED);
            ride.setDriver(driver);
        }else{
            throw new CannotAcceptRideException();
        }
        rideService.save(ride);
        RideResponseDTO response = new RideResponseDTO(ride, rejectionMessageService, passengerService, routeService);
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @PutMapping(value = "/{id}/end",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> endRide(@PathVariable("id") @NotNull Integer id,
                                                   Principal userPrincipal){
        Driver rideDriver = driverService.getDriverOfRide(id);
        if(!rideDriver.getEmail().equals(userPrincipal.getName())){
            throw new UserIdNotMatchingException();
        }
        Ride ride = rideService.findOneById(id);
        if(ride.getRideState() == RideState.STARTED){
            ride.setRideState(RideState.FINISHED);
            ride.setEndTime(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        }else{
            throw new CannotEndRideException();
        }
        rideService.save(ride);
        RideResponseDTO response = new RideResponseDTO(ride, rejectionMessageService, passengerService, routeService);
        return new ResponseEntity<RideResponseDTO>(response,HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @PutMapping(value = "/{id}/cancel",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> cancelWithExplanation(@PathVariable("id")@NotNull Integer id,
                                                                 @Valid @RequestBody ExplanationDTO explanation,
                                                                 Principal userPrincipal){
        Driver rideDriver = driverService.getDriverOfRide(id);
        if(!rideDriver.getEmail().equals(userPrincipal.getName())){
            throw new UserIdNotMatchingException();
        }

        Ride ride = rideService.findOneById(id);

        RejectionMessage rejectionMessage = new RejectionMessage(ride, explanation.getReason(),
                    (User) rideDriver, Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));


        if(ride.getRideState() == RideState.PENDING || ride.getRideState() == RideState.STARTED){
            ride.setRideState(RideState.REJECTED);
            ride.setRejectionMessage(rejectionMessage);
        }else{
            throw new CannotCancelRideException();
        }

        rideService.save(ride);
        RideResponseDTO response = new RideResponseDTO(ride, rejectionMessageService, passengerService, routeService);
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
        Integer user_id  =  favoriteRidesService.getUserOfRide(id);
        User user = userService.findUserByEmail(userPrincipal.getName());
        if(user.getId() != user_id){
            throw new UserIdNotMatchingException();
        }
        favoriteRidesService.delete(ride);
        return new ResponseEntity<>(HttpStatus.OK);
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
