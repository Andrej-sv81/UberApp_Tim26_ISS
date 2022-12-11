package com.example.demo.controller;

import com.example.demo.dto.MultipleReviewsDTO;
import com.example.demo.dto.OneRideAllReviewsDTO;
import com.example.demo.dto.ReviewRequestDTO;
import com.example.demo.dto.ReviewResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @PostMapping(value = "/{rideId}/vehicle/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewResponseDTO> createReviewVehicle(@PathVariable("id") int id, @PathVariable("rideId") int rideId, @RequestBody ReviewRequestDTO rating){
        ReviewResponseDTO response = new ReviewResponseDTO();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping(value = "/{rideId}/driver/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewResponseDTO> createReviewDriver(@PathVariable("id") int id, @PathVariable("rideId") int rideId, @RequestBody ReviewRequestDTO rating){
        ReviewResponseDTO response = new ReviewResponseDTO();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping(value = "/driver/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MultipleReviewsDTO> getReviewsDriver(@PathVariable("id") int id){
        MultipleReviewsDTO response = new MultipleReviewsDTO();
        return new ResponseEntity<MultipleReviewsDTO>(response,HttpStatus.OK);
    }

    @GetMapping(value = "/vehicle/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MultipleReviewsDTO> getReviewsVehicle(@PathVariable("id") int id){
        MultipleReviewsDTO response = new MultipleReviewsDTO();
        return new ResponseEntity<MultipleReviewsDTO>(response,HttpStatus.OK);
    }

    @GetMapping(value = "/{rideId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OneRideAllReviewsDTO> allReviewsOneRide(@PathVariable("rideId") int rideId){
        OneRideAllReviewsDTO response = new OneRideAllReviewsDTO();
        return new ResponseEntity<OneRideAllReviewsDTO>(response,HttpStatus.OK);
    }
}
