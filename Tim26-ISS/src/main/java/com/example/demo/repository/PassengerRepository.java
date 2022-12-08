package com.example.demo.repository;

import com.example.demo.dto.PassengerRequestDTO;
import com.example.demo.model.Passenger;
import com.example.demo.model.Ride;
import com.example.demo.service.PassengerService;

import java.util.Collection;

public interface PassengerRepository {

    Collection<Passenger> findAll();
    Passenger create(PassengerRequestDTO passenger);
    Passenger findOne(int id);
    Passenger update(Passenger passenger);
    Passenger activateAcc(int id);
    Collection<Ride> passengerGetRides(int id);
}
