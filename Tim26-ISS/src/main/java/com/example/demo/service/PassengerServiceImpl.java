package com.example.demo.service;

import com.example.demo.dto.PassengerRequestDTO;
import com.example.demo.dto.PassengerResponseDTO;
import com.example.demo.model.Passenger;
import com.example.demo.repository.InMemoryPassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PassengerServiceImpl implements PassengerService{

    @Autowired
    private InMemoryPassengerRepository passengerRepository;

    @Override
    public Collection<Passenger> findAll() {
        return passengerRepository.findAll();
    }

    @Override
    public Passenger findOne(int id) {
        return passengerRepository.findOne(id);
    }

    @Override
    public PassengerResponseDTO create(PassengerRequestDTO passenger) throws Exception {
//        if(passenger.getId() != 0){
//            throw new Exception("Id must be zero while initialization.");
//        }
        Passenger savedPassenger = passengerRepository.create(passenger);
        PassengerResponseDTO responsePassenger = new PassengerResponseDTO();
        return responsePassenger;
    }

    @Override
    public Passenger activateAcc(int activationId) {
        return null;
    }

    @Override
    public Passenger update(int id) throws Exception {
        Passenger passengerToUpdate = findOne(id);
        if(passengerToUpdate == null){
            throw new Exception("Passenger with this id not found.");
        }
        passengerToUpdate.setName("APDEJTOVANO");
        //Passenger updatedPassenger = passengerRepository.create(passengerToUpdate);
        //return updatedPassenger;
        return null;
    }
}
