package com.example.demo.controller;

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
}
