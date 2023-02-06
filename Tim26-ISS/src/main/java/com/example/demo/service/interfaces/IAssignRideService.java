package com.example.demo.service.interfaces;

import com.example.demo.controller.DriverController;
import com.example.demo.model.Driver;
import com.example.demo.model.Ride;

import javax.swing.event.ListDataEvent;
import java.util.List;

public interface IAssignRideService {
    public Driver assignDriver(Ride ride);
    public Driver pickDriver(List<Driver> drivers, Ride ride);
    public List<Driver> removeNonValidDrivers(List<Driver> drivers);
    public List<Driver> sortBusyDrivers(List<Driver> drivers,Ride ride);
    public List<Driver> sortFreeDrivers(List<Driver> drivers,Ride ride);
}
