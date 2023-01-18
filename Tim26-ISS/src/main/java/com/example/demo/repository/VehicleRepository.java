package com.example.demo.repository;

import com.example.demo.model.Review;
import com.example.demo.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    Vehicle findOneById(Integer id);

    @Query("select v.reviews from Vehicle v where v.id=?1")
    List<Review> getReviews(Integer id);
}
