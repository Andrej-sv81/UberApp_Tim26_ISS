package com.example.demo.service;


import com.example.demo.dto.DriverReportDTO;
import com.example.demo.dto.driver.DriverDocumentsRequestDTO;
import com.example.demo.dto.driver.DriverDocumentsResponseDTO;
import com.example.demo.dto.driver.DriverRequestDTO;
import com.example.demo.dto.driver.DriverResponseDTO;
import com.example.demo.exceptions.UserDoesNotExistException;
import com.example.demo.dto.driver.*;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.interfaces.IDriverService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import javax.transaction.Transactional;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.Optional;

@Service
public class DriverService implements IDriverService {

    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;
    @Autowired
    private RideRepository rideRepository;
    @Autowired
    private DocumentRepository documentRepository;


    @Override
    public List<User> getAll() {
        return driverRepository.findAll();
    }

    @Override
    public Driver findDriver(Integer driverId) {
        Optional<User> found = driverRepository.findById(driverId);
        if(found.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Driver not found in database.");
        }
        return (Driver) found.get();
    }

    @Override
    public DriverResponseDTO insert(Driver driver) {
        Driver saved;
        saved = driverRepository.save(driver);
        driverRepository.flush();
        return new DriverResponseDTO(saved);
    }

    @Override
    public DriverResponseDTO update(DriverRequestDTO driver, Integer id) {
        try{
            Driver driverFound  = findDriver(id); //thiss will throw exception if passenger not found
            driverFound.updateDriver(driver);
            driverRepository.save(driverFound);
            driverRepository.flush();
            return new DriverResponseDTO(driverFound);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Driver delete(Integer driverId) {
        Optional<User> found = driverRepository.findById(driverId);
        if (found.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Driver not found in database.");
        }
        driverRepository.delete(found.get());
        driverRepository.flush();
        return (Driver) found.get();
    }

    @Override
    public void deleteAll() {
        driverRepository.deleteAll();
        driverRepository.flush();
    }

    @Override
    public List<Ride> getRides(Integer id) {
        return driverRepository.getRides(id);
    }

    @Override
    public DriverDocumentsResponseDTO addDocument(Integer id, DriverDocumentsRequestDTO docs) {
        Optional<User> found = driverRepository.findById(id);
        if (found.isEmpty())
            throw new RuntimeException();
        Driver driver = (Driver) found.get();
        List<Document>documents =  driver.getDocuments();
        Document newDoc = new Document(docs);
        newDoc.setDriver(driver);
        documents.add(newDoc);
        return new DriverDocumentsResponseDTO(newDoc);
    }


    @Override
    public Driver getDriverOfRide(Integer id) {
        Optional<Driver> found = Optional.ofNullable(driverRepository.getDriverOfRide(id));
        if(found.isEmpty()){
            throw new UserDoesNotExistException();
        }
        return  found.get();
    }

    @Override
    public Driver findByEmail(String mail) {
        Optional<Driver> found = driverRepository.findByEmail(mail);
        if(found.isEmpty()){
            throw new UserDoesNotExistException();
        }
        return found.get();
    }
    @Transactional
    @Override
    public List<Document> getDocuments(Integer id) {
        return driverRepository.getDocuments(id);
    }

    @Override
    public DriverVehicleResponseDTO updateVehicle(Integer id, DriverVehicleRequestDTO newData) {
        Optional<Vehicle> found = vehicleRepository.findOneById(id); //thiss will throw exception if passenger not found
        Vehicle vehicle = found.get();
        vehicle.updateVehicle(newData);
        vehicle.setVehicleType(vehicleTypeRepository.findOneByName(VehicleTypeEnum.valueOf(newData.getVehicleType())));
        vehicleRepository.save(vehicle);
        vehicleRepository.flush();
        return new DriverVehicleResponseDTO(vehicle);
    }

    @Override
    public List<Driver> driversMatchingCriteria(Ride ride) {
        List<Driver> fitsCriteria = driverRepository.getActiveDrivers();
        if (fitsCriteria.isEmpty())
            return null;
        for (Driver d :fitsCriteria){
            Hibernate.initialize(d);
            if (d.getVehicle()==null){
                fitsCriteria.remove(d);
                continue;
            }
            if ((d.getVehicle().isPetFlag() && ride.isPetFlag() && d.getVehicle().isBabyFlag() && ride.isBabyFlag() && d.getVehicle().getVehicleType().equals(ride.getVehicleType())))
                fitsCriteria.remove(d);
        }
        List<Driver> availableFitsCriteria = removeReservedDrivers(fitsCriteria, ride);
        return availableFitsCriteria;
    }

    //Cheking drivers working hours in last 24h TO JE WORKING HOUR SERVICE FUNKCIJA

    @Override
    public boolean checkFutureRides(Integer driverId, Ride ride) {
        List<Ride> futureRides = rideRepository.acceptedRidesForDriver(driverId);
        if (futureRides.isEmpty())
            return false;
        for (Ride booked:futureRides){
            Hibernate.initialize(booked);
            if (overLappingRides(ride,booked))
                return true;
        }
        return false;
    }

    @Override
    public List<Driver> findFreeDrivers(Ride ride) {
        List<Driver> activeAvailableDrivers = driversMatchingCriteria(ride);
        for (Driver driver:activeAvailableDrivers){
            //TODO proveri da li je trenutno dostupan NEMA NIJEDNU VOZNJU TRENUTNO - FOR EACH DRIVER CALL CHECKFUTURERIDES
            if (rideRepository.findActiveRideForDriver(driver.getId()) != null){
                activeAvailableDrivers.remove(driver);   // driver currently not available
            }
        }
        return activeAvailableDrivers;
    }

    @Override
    public List<Driver> removeReservedDrivers(List<Driver> drivers,Ride ride) {
        if (drivers.isEmpty())
            return new ArrayList<Driver>();
        for (Driver driver:drivers){
            if (checkFutureRides(driver.getId(),ride)){
                drivers.remove(driver);
            }
        }
        return drivers;
    }

    // provera za dve voznje da li se preklapaju da li jedna pocinje,traje ili zavrsava u vreme druge -> VRACA TRUE AKO SE PREKLAPAJU
    @Override
    public boolean overLappingRides(Ride request, Ride booked) {
        Hibernate.initialize(booked);
        Date endRequest = Date.from(request.getScheduledTime().toInstant().plus(Duration.ofMinutes(request.getEstimatedTime())));
        Date endBooked = Date.from(booked.getScheduledTime().toInstant().plus(Duration.ofMinutes(booked.getEstimatedTime())));
        if (request.getScheduledTime().after(booked.getScheduledTime()) && request.getScheduledTime().before(endBooked))
            return true;
        else if (endRequest.after(booked.getScheduledTime()) && endRequest.before(endBooked))
            return true;
        else if (request.getScheduledTime().before(booked.getScheduledTime()) && endRequest.after(endBooked))
            return true;
        return false;
    }

    @Override
    public DriverReportDTO getReports(Integer driverId) {

        List<Integer> numberOfRides = new ArrayList<>();
        List<Integer> sumOfPrices = new ArrayList<>();

        for(int i = 1; i<13; i++) {
            Integer count = rideRepository.getNumOfRides(driverId, i).isPresent() ?  rideRepository.getNumOfRides(driverId, i).get() : 0;
            Integer sum = rideRepository.getSumOfPrices(driverId, i).isPresent() ? rideRepository.getSumOfPrices(driverId, i).get() : 0;
            numberOfRides.add(count);
            sumOfPrices.add(sum);
        }

        DriverReportDTO response = new DriverReportDTO(numberOfRides, sumOfPrices);
        return response;
    }

}
