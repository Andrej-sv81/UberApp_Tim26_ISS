package com.example.demo.service;


import com.example.demo.exceptions.UserDoesNotExistException;
import org.springframework.data.domain.Page;
import com.example.demo.dto.passenger.PassengerRequestDTO;
import com.example.demo.dto.passenger.PassengerResponseDTO;
import com.example.demo.model.Passenger;
import com.example.demo.model.User;
import com.example.demo.model.Passenger;
import com.example.demo.model.Ride;
import com.example.demo.repository.PassengerRepository;
import com.example.demo.service.interfaces.IPassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class PassengerService implements IPassengerService{


    @Autowired
    private PassengerRepository passengerRepository;

    @Override
    public List<User> getAll() {
        return passengerRepository.findAll();
    }

    @Override
    public Passenger findPassenger(Integer passengerId) {
        Optional<User> found = passengerRepository.findById(passengerId);
        if(found.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Passenger not found in database.");
        }
        return (Passenger) found.get();
    }

    @Override
    public PassengerResponseDTO insert(Passenger passenger) {
        Passenger saved;
        saved = passengerRepository.save(passenger);
        passengerRepository.flush();
        return new PassengerResponseDTO(saved);
    }

    @Override
    public PassengerResponseDTO update(PassengerRequestDTO edited, Integer id) {
        try{
            Passenger passenger  = findPassenger(id); //thiss will throw exception if passenger not found
            passenger.updatePassenger(edited);
            passengerRepository.save(passenger);
            passengerRepository.flush();
            return new PassengerResponseDTO(passenger);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Passenger delete(Integer passengerId) {
        Optional<User> found = passengerRepository.findById(passengerId);
        if (found.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Passenger not found in database.");
        }
        passengerRepository.delete(found.get());
        passengerRepository.flush();
        return (Passenger) found.get();
    }

    @Override
    public void deleteAll() {
        passengerRepository.deleteAll();
        passengerRepository.flush();
    }

    @Override
    public List<Ride> getRides(Integer id) {
        return passengerRepository.getRides(id);
    }

    @Override
    public Passenger findPassengerByEmail(String mail) {
        Optional<User> found = passengerRepository.findByEmail(mail);
        if(found.isEmpty()){
            throw new UserDoesNotExistException();
        }
        return (Passenger) found.get();
    }

    @Override
    public List<Passenger> getPassengersOfRide(Integer id) {
        return passengerRepository.getPassengerOfRide(id);
    }
}