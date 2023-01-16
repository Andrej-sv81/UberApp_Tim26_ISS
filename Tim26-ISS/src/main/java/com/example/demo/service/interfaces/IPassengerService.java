package com.example.demo.service.interfaces;

import com.example.demo.model.Passenger;
import com.example.demo.model.Ride;

import java.util.List;

public interface IPassengerService {

    List<Ride> getRides(Integer id);
}
