package com.example.demo.service;

import com.example.demo.exceptions.UserDoesNotExistException;
import com.example.demo.exceptions.VehicleDoesNotExistException;
import com.example.demo.model.Review;
import com.example.demo.model.User;
import com.example.demo.model.Vehicle;
import com.example.demo.repository.VehicleRepository;
import com.example.demo.service.interfaces.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService implements IVehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public Vehicle findOneById(Integer id) {
        Optional<Vehicle> found = vehicleRepository.findOneById(id);
        if (found.isEmpty()) {
            throw new VehicleDoesNotExistException();
        }
        return found.get();
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
