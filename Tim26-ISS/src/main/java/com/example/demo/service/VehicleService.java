package com.example.demo.service;

import com.example.demo.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;
}
