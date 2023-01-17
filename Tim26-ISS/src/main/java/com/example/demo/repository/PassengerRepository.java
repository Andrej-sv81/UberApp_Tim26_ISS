package com.example.demo.repository;


import com.example.demo.model.Passenger;
import com.example.demo.model.Ride;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PassengerRepository extends UserRepository{
    @Query("select p.rides from Passenger p where p.id=?1")
    List<Ride> getRides(Integer id);

   // Page<Ride> getRides(Pageable pageable);
}
