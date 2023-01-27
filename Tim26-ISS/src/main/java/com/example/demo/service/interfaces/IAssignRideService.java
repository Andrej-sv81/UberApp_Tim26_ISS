package com.example.demo.service.interfaces;

import com.example.demo.model.Driver;
import com.example.demo.model.Ride;

import javax.swing.event.ListDataEvent;
import java.util.List;

public interface IAssignRideService {
    public Driver assignDriver(Ride ride);
}
