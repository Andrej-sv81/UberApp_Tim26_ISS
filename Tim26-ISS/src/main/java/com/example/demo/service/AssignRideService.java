package com.example.demo.service;

import com.example.demo.model.Driver;
import com.example.demo.model.Ride;
import com.example.demo.repository.DriverRepository;
import com.example.demo.service.interfaces.IAssignRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignRideService implements IAssignRideService {
    @Autowired
    private DriverRepository driverRepository;

    @Override
    public Driver assignDriver(Ride ride) {
        List<Driver> driverSelection = driverRepository.getActiveDrivers();
        return driverSelection.get(0);
    }
}
