package com.example.demo.repository;

import com.example.demo.model.Driver;
import com.example.demo.model.Review;
import com.example.demo.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Integer> {
    public Ride findOneById(Integer id);

    @Query("select r.driver from Ride r where r.id =?1")
    Driver getDriverOfRide(int rideId);
    @Query("select r.reviews from Ride r where r.id=?1")
    List<Review> getReviews(Integer id);
}
