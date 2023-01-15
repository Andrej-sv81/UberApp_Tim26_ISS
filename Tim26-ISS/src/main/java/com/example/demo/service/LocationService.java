package com.example.demo.service;

import com.example.demo.model.Location;
import com.example.demo.repository.LocationRepository;
import com.example.demo.service.interfaces.ILocationServicec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService implements ILocationServicec {

    @Autowired
    private LocationRepository locationRepository;

    public void delete(Location location) {
        locationRepository.delete(location);
    }
    @Override
    public void save(Location location) {
        locationRepository.save(location);
    }

}
