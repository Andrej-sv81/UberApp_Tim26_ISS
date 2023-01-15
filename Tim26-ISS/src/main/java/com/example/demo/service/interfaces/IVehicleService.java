package com.example.demo.service.interfaces;

import com.example.demo.model.Vehicle;

public interface IVehicleService {
    Vehicle findOneById(Integer id);

    void save(Vehicle vehicle);
}
