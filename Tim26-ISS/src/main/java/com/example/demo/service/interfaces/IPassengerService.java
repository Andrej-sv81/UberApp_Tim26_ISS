package com.example.demo.service.interfaces;

import com.example.demo.dto.passenger.PassengerRequestDTO;
import com.example.demo.dto.passenger.PassengerResponseDTO;
import com.example.demo.model.Passenger;
import com.example.demo.model.User;

import java.util.List;

public interface IPassengerService {
    public List<User> getAll();
    public Passenger findPassenger(Integer passengerId);
    public PassengerResponseDTO insert(Passenger passenger);
    public PassengerResponseDTO update(PassengerRequestDTO passenger, Integer id);
    public Passenger delete(Integer studentId);
    public void deleteAll();
}
