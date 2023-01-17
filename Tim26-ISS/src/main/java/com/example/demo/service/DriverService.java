package com.example.demo.service;

import com.example.demo.model.Driver;
import com.example.demo.model.Ride;
import com.example.demo.repository.DriverRepository;
import com.example.demo.service.interfaces.IDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService implements IDriverService {

    @Autowired
    private DriverRepository driverRepository;


    @Override
    public List<Ride> getRides(Integer id) {
        return driverRepository.getRides(id);
    }
}
