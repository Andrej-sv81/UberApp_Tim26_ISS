package com.example.demo.controller;

import com.example.demo.dto.*;
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
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

@RestController
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
    //TODO Provjera JSON-a, PRINCIPAL objekat, ID korisnika
    //TODO Global ERROR handler
    @PreAuthorize("hasAuthority('ROLE_PASSENGER')")
    @PostMapping(value = "/{rideId}/vehicle",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createReviewVehicle(@PathVariable("rideId") int rideId,
                                                                 @RequestBody ReviewRequestDTO rating,
                                                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){
        String jwtToken = authorization.substring(authorization.indexOf("Bearer ") + 7);
        String mail = jwtTokenUtil.getUsernameFromToken(jwtToken);
        Passenger passenger = passengerService.findPassengerByEmail(mail);
        Ride ride = rideService.findOneById(rideId);
        if(ride == null) {
            HttpStatusMessageDTO errorResponse = new HttpStatusMessageDTO("Ride does not exist!");
            return new ResponseEntity<HttpStatusMessageDTO>(errorResponse, HttpStatus.NOT_FOUND);
        }
        Driver driver = ride.getDriver();
        Vehicle vehicle = driver.getVehicle();
        if(vehicle == null) {
            HttpStatusMessageDTO errorResponse = new HttpStatusMessageDTO("Vehicle does not exist!");
            return new ResponseEntity<HttpStatusMessageDTO>(errorResponse, HttpStatus.NOT_FOUND);
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
    //TODO Provjera JSON-a, PRINCIPAL objekat, ID korisnika
    //TODO Global ERROR handler
    @PreAuthorize("hasAuthority('ROLE_PASSENGER')")
    @PostMapping(value = "/{rideId}/driver",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createReviewDriver(@PathVariable("id") int id, @PathVariable("rideId") int rideId,
                                                @RequestBody ReviewRequestDTO rating,
                                                @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){
        String jwtToken = authorization.substring(authorization.indexOf("Bearer ") + 7);
        String mail = jwtTokenUtil.getUsernameFromToken(jwtToken);
        Passenger passenger = passengerService.findPassengerByEmail(mail);
        Ride ride = rideService.findOneById(rideId);
        if(ride == null) {
            HttpStatusMessageDTO errorResponse = new HttpStatusMessageDTO("Ride does not exist!");
            return new ResponseEntity<HttpStatusMessageDTO>(errorResponse, HttpStatus.NOT_FOUND);
        }
        Driver driver = ride.getDriver();
        Review review = new Review(rating,ride,passenger);
        reviewService.save(review);
        ride.setReviews(rideService.getReviews(ride.getId()));
        List<Review> reviewList = ride.getReviews();
        reviewList.add(review);
        ride.setReviews(reviewList);
        rideService.save(ride);
        ReviewResponseDTO response = new ReviewResponseDTO(review, passenger);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    //TODO Provjera JSON-a, PRINCIPAL objekat, ID korisnika
    //TODO Global ERROR handler
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @GetMapping(value = "/driver/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getReviewsDriver(@PathVariable("id") int id){
        Ride ride = rideService.findOneById(id);
        if(ride == null) {
            HttpStatusMessageDTO errorResponse = new HttpStatusMessageDTO("Driver does not exist!");
            return new ResponseEntity<HttpStatusMessageDTO>(errorResponse, HttpStatus.NOT_FOUND);
        }
        ride.setReviews(rideService.getReviews(ride.getId()));
        List<Review> reviews = ride.getReviews();
        List<ReviewResponseDTO> responseList = new ArrayList<ReviewResponseDTO>();
        for(Review review: reviews){
            responseList.add(new ReviewResponseDTO(review));
        }
        MultipleDTO response = new MultipleDTO();
        response.setTotalCount(responseList.size());
        response.setResults(responseList);
        return new ResponseEntity<MultipleDTO>(response,HttpStatus.OK);
    }
    //TODO Provjera JSON-a, PRINCIPAL objekat, ID korisnika
    //TODO Global ERROR handler
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @GetMapping(value = "/vehicle/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getReviewsVehicle(@PathVariable("id") int id){
        Vehicle vehicle = vehicleService.findOneById(id);
        if(vehicle == null) {
            HttpStatusMessageDTO errorResponse = new HttpStatusMessageDTO("Vehicle does not exist!");
            return new ResponseEntity<HttpStatusMessageDTO>(errorResponse, HttpStatus.NOT_FOUND);
        }
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
    //TODO Provjera JSON-a, PRINCIPAL objekat, ID korisnika
    //TODO Global ERROR handler
    @GetMapping(value = "/{rideId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> allReviewsOneRide(@PathVariable("rideId") int rideId){
        Ride ride = rideService.findOneById(rideId);
        Vehicle vehicle = vehicleService.findOneById(ride.getDriver().getVehicle().getId());
        if(ride == null) {
            HttpStatusMessageDTO errorResponse = new HttpStatusMessageDTO("Ride does not exist!");
            return new ResponseEntity<HttpStatusMessageDTO>(errorResponse, HttpStatus.NOT_FOUND);
        }
        ride.setReviews(rideService.getReviews(ride.getId()));
        vehicle.setReviews(vehicleService.getReviews(vehicle.getId()));
        List<Review> reviewsDriver = ride.getReviews();
        List<ReviewResponseDTO> responseListDriver = new ArrayList<ReviewResponseDTO>();
        List<Review> reviewsVehicle = vehicle.getReviews();
        List<ReviewResponseDTO> responseListVehicle = new ArrayList<ReviewResponseDTO>();
        for(Review review: reviewsDriver){
            responseListDriver.add(new ReviewResponseDTO(review));
        }
        for(Review review: reviewsVehicle){
            responseListVehicle.add(new ReviewResponseDTO(review));
        }
        List<OneRideAllReviewsDTO> response = new ArrayList<OneRideAllReviewsDTO>();
        int to = max(responseListDriver.size(), responseListVehicle.size());
        for(int i = 0; i<to; i++){
            response.add(new OneRideAllReviewsDTO(responseListDriver.get(i), responseListVehicle.get(i)));
        }
        return new ResponseEntity<List<OneRideAllReviewsDTO>>(response,HttpStatus.OK);
    }
}
