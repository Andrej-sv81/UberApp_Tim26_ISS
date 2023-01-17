package com.example.demo.service.interfaces;

import com.example.demo.model.Review;
import com.example.demo.model.Vehicle;

import java.util.List;

public interface IVehicleService {
    Vehicle findOneById(Integer id);

    void save(Vehicle vehicle);


    List<Review> getReviews(Integer id);
}
