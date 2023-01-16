package com.example.demo.service;

import com.example.demo.model.Passenger;
import com.example.demo.model.Ride;
import com.example.demo.repository.PassengerRepository;
import com.example.demo.service.interfaces.IPassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService implements IPassengerService {

    @Autowired
    private PassengerRepository passengerRepository;


    @Override
    public List<Ride> getRides(Integer id) {
        return passengerRepository.getRides(id);
    }
}