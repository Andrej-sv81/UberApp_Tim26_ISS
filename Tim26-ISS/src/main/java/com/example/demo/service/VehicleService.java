package com.example.demo.service;

import com.example.demo.model.Review;
import com.example.demo.model.Vehicle;
import com.example.demo.repository.VehicleRepository;
import com.example.demo.service.interfaces.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class VehicleService implements IVehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public Vehicle findOneById(Integer id) {
        return vehicleRepository.findOneById(id);
    }

    @Override
    public void save(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
        vehicleRepository.flush();
    }

    @Override
    @Transactional
    public List<Review> getReviews(Integer id) {
        return vehicleRepository.getReviews(id);
    }


}
