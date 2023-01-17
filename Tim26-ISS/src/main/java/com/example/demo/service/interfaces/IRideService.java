package com.example.demo.service.interfaces;

import com.example.demo.model.Driver;
import com.example.demo.model.Review;
import com.example.demo.model.Ride;

import java.util.List;

public interface IRideService {
   Ride findOneById(Integer id);

   Driver getDriverOfRide(int rideId);

    void save(Ride ride);

    List<Review> getReviews(Integer id);
}
