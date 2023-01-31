package com.example.demo.service;

import com.example.demo.model.Driver;
import com.example.demo.model.Ride;
import com.example.demo.repository.DriverRepository;
import com.example.demo.service.interfaces.IAssignRideService;
import com.example.demo.util.cost.EstimatedCost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignRideService implements IAssignRideService {
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private DriverService driverService;

    @Override
    public Driver assignDriver(Ride ride) {
        Driver selectedDriver = null;
        List<Driver> driverSelection = driverService.driversMatchingCriteria(ride); // aktivni sa odgovarajucim zahtevima, i slobodni i zauzeti ako nema slobodnih bice samo zauzeti
        List<Driver> freeDrivers = driverService.findFreeDrivers(ride);  // ovde su trenutno slobodni, ostali vozaci ce biti u driverSelection
        if (driverSelection.isEmpty())
            return selectedDriver;    // ne postoji aktivni vozac sa odgovarajucim zahtevima
        if (freeDrivers.isEmpty()){
            // TODO ispravi ovo sortiranje uzmi aktivnu voznju iz vozaca i nadji gde je kraj voznje ovo sortiranje ispod je za slobodne vozace
            List<Driver> sortedBusy = driverSelection.stream().sorted((d1,d2)->
                    (int) EstimatedCost.calculateDistance(d1.getVehicle().getLocation().getLatitude(), d1.getVehicle().getLocation().getLatitude(),
                            ride.getRoutes().get(0).getStartLocation().getLatitude(),ride.getRoutes().get(0).getStartLocation().getLongitude()
                    -EstimatedCost.calculateDistance(d2.getVehicle().getLocation().getLatitude(), d2.getVehicle().getLocation().getLatitude()
                                    ,ride.getRoutes().get(0).getStartLocation().getLatitude(),ride.getRoutes().get(0).getStartLocation().getLongitude()))).collect(Collectors.toList());
            // dodaj funkciju za biranje vozaca
        }
        List<Driver> sortedFree = freeDrivers.stream().sorted((d1,d2)->
                (int) EstimatedCost.calculateDistance(d1.getVehicle().getLocation().getLatitude(), d1.getVehicle().getLocation().getLatitude(),
                        ride.getRoutes().get(0).getStartLocation().getLatitude(),ride.getRoutes().get(0).getStartLocation().getLongitude()
                                -EstimatedCost.calculateDistance(d2.getVehicle().getLocation().getLatitude(), d2.getVehicle().getLocation().getLatitude()
                                ,ride.getRoutes().get(0).getStartLocation().getLatitude(),ride.getRoutes().get(0).getStartLocation().getLongitude()))).collect(Collectors.toList());

        // TODO napisi funkciju za biranje vozaca
        return selectedDriver;
    }

    @Override
    public Driver pickDriver(List<Driver> drivers) {
        while (!drivers.isEmpty()){
            // TODO check if driver rejected ride if rejected remove from list else return driver
        }
        return null;
    }


}
