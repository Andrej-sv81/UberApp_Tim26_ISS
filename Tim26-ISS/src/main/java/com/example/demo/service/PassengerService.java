package com.example.demo.service;

import com.example.demo.dto.PassengerRequestDTO;
import com.example.demo.dto.PassengerResponseDTO;
import com.example.demo.model.Passenger;
import org.springframework.stereotype.Service;

import java.util.Collection;

public interface PassengerService {

    Collection<Passenger> findAll();
    Passenger findOne(int id);
    PassengerResponseDTO create(PassengerRequestDTO passenger) throws Exception;
    Passenger activateAcc(int activationId);
    Passenger update(int id) throws Exception;
}
