package com.example.demo.service;

import com.example.demo.dto.driver.DriverDocumentsRequestDTO;
import com.example.demo.dto.driver.DriverDocumentsResponseDTO;
import com.example.demo.dto.driver.DriverRequestDTO;
import com.example.demo.dto.driver.DriverResponseDTO;
import com.example.demo.exceptions.UserDoesNotExistException;
import com.example.demo.model.*;
import com.example.demo.repository.DriverRepository;
import com.example.demo.service.interfaces.IDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;
import java.util.Optional;

@Service
public class DriverService implements IDriverService {

    @Autowired
    private DriverRepository driverRepository;


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

}
