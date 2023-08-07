package com.example.demo.service;

import com.example.demo.model.Driver;
import com.example.demo.model.Ride;
import com.example.demo.repository.DriverRepository;
import com.example.demo.repository.WorkingHourRepository;
import com.example.demo.service.interfaces.IAssignRideService;
import com.example.demo.util.cost.EstimatedCost;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AssignRideService implements IAssignRideService {
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private DriverService driverService;
    private Map<Integer,ArrayList<Driver>> rejectedRides= new HashMap<Integer,ArrayList<Driver>>();   // vozaci koji su odbili zadatu voznju
    @Autowired
    private WorkingHourRepository workingHourRepository;
    @Autowired
    private WorkingHourService workingHourService;

    @Override
    public Driver assignDriver(Ride ride) {
        Driver selectedDriver = null;
        List<Driver> activeDrivers = driverRepository.getActiveDrivers();
        selectedDriver = activeDrivers.get(0);
        return selectedDriver;
    }

    @Override
    public Driver pickDriver(List<Driver> drivers,Ride ride) {
        while (!drivers.isEmpty()){
            // TODO check if driver rejected ride if rejected remove from list else return driver
            Driver d = drivers.get(0);
            if (!rejectedRides.containsKey(ride.getId())){
                return d;
            }
            List<Driver> driversThatRejected = rejectedRides.get(ride.getId());
            if (driversThatRejected.contains(d)){
                drivers.remove(d);
            }
            return drivers.get(0);
        }
        return null;
    }

    @Override
    public List<Driver> removeNonValidDrivers(List<Driver> drivers) {
        for (Driver d : drivers){
//            if (!workingHourService.validateDriverWorkingHours(d.getId())){
//                drivers.remove(d);
//            }
        }
        return drivers;
    }






}
