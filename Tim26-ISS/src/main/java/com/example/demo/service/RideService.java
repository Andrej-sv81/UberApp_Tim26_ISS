package com.example.demo.service;

import com.example.demo.model.Driver;
import com.example.demo.model.Review;
import com.example.demo.model.Ride;
import com.example.demo.repository.RideRepository;
import com.example.demo.service.interfaces.IRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RideService implements IRideService {

    @Autowired
    private RideRepository rideRepository;

    public Ride findOneById(Integer id) {
        return rideRepository.findOneById(id);
    }

    @Override
    public Driver getDriverOfRide(int rideId) {
        return rideRepository.getDriverOfRide(rideId);
    }

    @Override
    public void save(Ride ride) {
        rideRepository.save(ride);
        rideRepository.flush();
    }
    @Transactional
    @Override
    public List<Review> getReviews(Integer id) {
        return rideRepository.getReviews(id);
    }
}
