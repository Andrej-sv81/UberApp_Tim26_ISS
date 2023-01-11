package com.example.demo.service;

import com.example.demo.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassengerService{

    @Autowired
    private PassengerRepository passengerRepository;
}