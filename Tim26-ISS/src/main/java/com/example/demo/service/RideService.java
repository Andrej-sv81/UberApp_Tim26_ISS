package com.example.demo.service;

import com.example.demo.model.Ride;
import com.example.demo.repository.RideRepository;
import com.example.demo.service.interfaces.IRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RideService implements IRideService {

    @Autowired
    private RideRepository rideRepository;

    public Ride findOneById(Integer id) {
        return rideRepository.findOneById(id);
    }
}
