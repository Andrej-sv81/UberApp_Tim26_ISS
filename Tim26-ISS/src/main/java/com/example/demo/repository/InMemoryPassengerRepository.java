package com.example.demo.repository;

import com.example.demo.dto.PassengerRequestDTO;
import com.example.demo.dto.PassengerResponseDTO;
import com.example.demo.model.Passenger;
import com.example.demo.model.Ride;
import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryPassengerRepository implements  PassengerRepository{

    private static int counter = 0;

    private ConcurrentMap<Integer, Passenger> passengers = new ConcurrentHashMap<Integer,Passenger>();

    @Override
    public Collection<Passenger> findAll() {
        return this.passengers.values();
    }

    @Override
    public Passenger create(PassengerRequestDTO passenger) {
        Passenger created = new Passenger();


        int id = counter + 1;
        this.counter += 1;
        created.setId(id);

        this.passengers.put(id,created);
        return created;
    }

    @Override
    public Passenger findOne(int id) {
        return this.passengers.get(id);
    }

    @Override
    public Passenger update(Passenger passenger) {
        int id = passenger.getId();
        if (id != -1){
            this.passengers.put(id,passenger);
        }
        return passenger;
    }

    @Override
    public Passenger activateAcc(int id) {
        return null;
    }

    @Override
    public Collection<Ride> passengerGetRides(int id) {
        return null;
    }
}
