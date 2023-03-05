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
        //List<Driver> driverSelection = driverService.driversMatchingCriteria(ride); // aktivni sa odgovarajucim zahtevima, i slobodni i zauzeti ako nema slobodnih bice samo zauzeti
        //List<Driver> freeDrivers = driverService.findFreeDrivers(ride);  // ovde su trenutno slobodni, ostali vozaci ce biti u driverSelection
//        if (driverSelection.isEmpty())
//            return selectedDriver;    // ne postoji aktivni vozac sa odgovarajucim zahtevima
//        driverSelection = removeNonValidDrivers(driverSelection);
//        freeDrivers = removeNonValidDrivers(freeDrivers);
//        List<Driver> sortedBusy = new ArrayList<Driver>();
//        if (freeDrivers.isEmpty()){
//            sortedBusy = sortBusyDrivers(driverSelection,ride);
//            return pickDriver(sortedBusy,ride);
//        }
//        List<Driver> sortedFree = sortFreeDrivers(freeDrivers,ride);
//        selectedDriver = pickDriver(sortedFree,ride);
//        if (selectedDriver==null){
//            sortedBusy = sortBusyDrivers(driverSelection,ride);
//            return pickDriver(sortedBusy,ride);
//        }
        //Hibernate.initialize(selectedDriver);
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
            if (!workingHourService.validateDriverWorkingHours(d.getId())){
                drivers.remove(d);
            }
        }
        return drivers;
    }

    @Override
    public List<Driver> sortBusyDrivers(List<Driver> driverSelection, Ride ride) {
        List<Driver> sortedBusy = driverSelection.stream().sorted((d1,d2)->
                (int) EstimatedCost.calculateDistance(d1.getVehicle().getLocation().getLatitude(), d1.getVehicle().getLocation().getLatitude(),
                        ride.getRoutes().get(0).getStartLocation().getLatitude(),ride.getRoutes().get(0).getStartLocation().getLongitude()
                                -EstimatedCost.calculateDistance(d2.getVehicle().getLocation().getLatitude(), d2.getVehicle().getLocation().getLatitude()
                                ,ride.getRoutes().get(0).getStartLocation().getLatitude(),ride.getRoutes().get(0).getStartLocation().getLongitude()))).collect(Collectors.toList());
        return sortedBusy;
    }

    @Override
    public List<Driver> sortFreeDrivers(List<Driver> freeDrivers, Ride ride) {
        List<Driver> sortedFree = freeDrivers.stream().sorted((d1,d2)->
                (int) EstimatedCost.calculateDistance(d1.getVehicle().getLocation().getLatitude(), d1.getVehicle().getLocation().getLatitude(),
                        ride.getRoutes().get(0).getStartLocation().getLatitude(),ride.getRoutes().get(0).getStartLocation().getLongitude()
                                -EstimatedCost.calculateDistance(d2.getVehicle().getLocation().getLatitude(), d2.getVehicle().getLocation().getLatitude()
                                ,ride.getRoutes().get(0).getStartLocation().getLatitude(),ride.getRoutes().get(0).getStartLocation().getLongitude()))).collect(Collectors.toList());
        return sortedFree;
    }


}
