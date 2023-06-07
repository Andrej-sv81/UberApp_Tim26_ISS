package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.exceptions.UserIdNotMatchingException;
import com.example.demo.exceptions.VehicleDoesNotExistException;
import com.example.demo.model.*;
import com.example.demo.security.JwtTokenUtil;
import com.example.demo.service.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

@RestController
@Validated
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    RideService rideService;

    @Autowired
    VehicleService vehicleService;
    @Autowired
    ReviewService reviewService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    PassengerService passengerService;

    @Autowired
    DriverService driverService;

    @PreAuthorize("hasAuthority('ROLE_PASSENGER')")
    @PostMapping(value = "/{rideId}/vehicle",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createReviewVehicle(@PathVariable("rideId") @NotNull Integer rideId,
                                                 @Valid @RequestBody ReviewRequestDTO rating,
                                                 Principal userPrincipal){
        String mail = userPrincipal.getName();
        Passenger passenger = passengerService.findPassengerByEmail(mail);
        Ride ride = rideService.findOneById(rideId);
        Driver driver = driverService.findDriver(ride.getDriver().getId());
        Vehicle vehicle = vehicleService.findOneById(driver.getVehicle().getId());

        if(vehicle == null) {
            throw new VehicleDoesNotExistException();
        }

        Review review = new Review(rating,ride,passenger);
        reviewService.save(review);

        vehicle.setReviews(vehicleService.getReviews(vehicle.getId()));
        List<Review> reviewList = vehicle.getReviews();
        reviewList.add(review);
        vehicle.setReviews(reviewList);
        vehicleService.save(vehicle);

        ReviewResponseDTO response = new ReviewResponseDTO(review, passenger);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_PASSENGER')")
    @PostMapping(value = "/{rideId}/driver",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createReviewDriver(@PathVariable("rideId") @NotNull Integer rideId,
                                                @Valid @RequestBody ReviewRequestDTO rating,
                                                Principal userPrincipal){
        String mail = userPrincipal.getName();
        Passenger passenger = passengerService.findPassengerByEmail(mail);
        Ride ride = rideService.findOneById(rideId);

        Review review = new Review(rating,ride,passenger);
        review.setNotVehicle(true);
        reviewService.save(review);

        ReviewResponseDTO response = new ReviewResponseDTO(review, passenger);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @GetMapping(value = "/driver/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getReviewsDriver(@PathVariable("id") @NotNull  Integer id){
        Ride ride = rideService.findOneById(id);
        ride.setReviews(rideService.getReviews(ride.getId()));
        List<Review> reviews = ride.getReviews();
        List<ReviewResponseDTO> responseList = new ArrayList<ReviewResponseDTO>();
        for(Review review: reviews){
            if(review.isNotVehicle()){
                responseList.add(new ReviewResponseDTO(review));
            }
        }
        MultipleDTO response = new MultipleDTO();
        response.setTotalCount(responseList.size());
        response.setResults(responseList);
        return new ResponseEntity<MultipleDTO>(response,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @GetMapping(value = "/vehicle/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getReviewsVehicle(@PathVariable("id") @NotNull  Integer id){
        Vehicle vehicle = vehicleService.findOneById(id);
        vehicle.setReviews(vehicleService.getReviews(vehicle.getId()));
        List<Review> reviews = vehicle.getReviews();
        List<ReviewResponseDTO> responseList = new ArrayList<ReviewResponseDTO>();
        for(Review review: reviews){
            responseList.add(new ReviewResponseDTO(review));
        }
        MultipleDTO response = new MultipleDTO();
        response.setTotalCount(responseList.size());
        response.setResults(responseList);
        return new ResponseEntity<MultipleDTO>(response,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_PASSENGER') || hasAuthority('ROLE_DRIVER')")
    @GetMapping(value = "/{rideId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> allReviewsOneRide(@PathVariable("rideId") @NotNull  Integer rideId){
        Ride ride = rideService.findOneById(rideId);
        ride.setReviews(rideService.getReviews(ride.getId()));
        List<Review> reviews = ride.getReviews();
        List<ReviewResponseDTO> responseListDriver = new ArrayList<ReviewResponseDTO>();
        List<ReviewResponseDTO> responseListVehicle = new ArrayList<ReviewResponseDTO>();

        for(Review review: reviews){
            if(review.isNotVehicle()){
                responseListDriver.add(new ReviewResponseDTO(review));
            }else{
                responseListVehicle.add(new ReviewResponseDTO(review));
            }
        }
        List<OneRideAllReviewsDTO> response = new ArrayList<OneRideAllReviewsDTO>();
        int to = max(responseListDriver.size(), responseListVehicle.size());
        for(int i = 0; i<to; i++){
            ReviewResponseDTO driver;
            ReviewResponseDTO vehicle;
            try{
               driver = responseListDriver.get(i);
            }catch(IndexOutOfBoundsException e){
                driver = null;
            }
            try{
                vehicle = responseListVehicle.get(i);
            }catch(IndexOutOfBoundsException e){
                vehicle = null;
            }
            response.add(new OneRideAllReviewsDTO(vehicle, driver));
        }
        return new ResponseEntity<List<OneRideAllReviewsDTO>>(response,HttpStatus.OK);
    }
}
