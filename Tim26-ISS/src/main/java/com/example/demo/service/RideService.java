package com.example.demo.service;

import com.example.demo.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RideService{

    @Autowired
    private RideRepository rideRepository;

}
