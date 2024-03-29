package com.example.demo.service.interfaces;

import com.example.demo.dto.DriverReportDTO;
import com.example.demo.dto.driver.*;
import com.example.demo.model.Document;
import com.example.demo.model.Driver;
import com.example.demo.model.Ride;
import com.example.demo.model.User;

import java.util.List;

public interface IDriverService {
    public List<User> getAll();
    public Driver findDriver(Integer driverId);
    public DriverResponseDTO insert(Driver driver);
    public DriverResponseDTO update(DriverRequestDTO driver, Integer id);
    public Driver delete(Integer driverId);
    public void deleteAll();
    List<Ride> getRides(Integer id);
    DriverDocumentsResponseDTO addDocument(Integer id, DriverDocumentsRequestDTO docs);

    Driver getDriverOfRide(Integer id);

    Driver findByEmail(String name);
    List<Document> getDocuments(Integer id);
    public DriverVehicleResponseDTO updateVehicle(Integer id,DriverVehicleRequestDTO newData);
    public List<Driver> driversMatchingCriteria(Ride ride);
    public boolean checkFutureRides(Integer driverId,Ride ride);
    public List<Driver> findFreeDrivers(Ride ride);
    public List<Driver> removeReservedDrivers(List<Driver> drivers,Ride ride);
    public boolean overLappingRides(Ride ride1,Ride ride2);

    DriverReportDTO getReports(Integer driverId);
}
