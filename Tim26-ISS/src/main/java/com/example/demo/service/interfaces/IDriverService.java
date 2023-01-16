package com.example.demo.service.interfaces;

import com.example.demo.model.Driver;
import com.example.demo.model.Ride;

import java.util.List;

public interface IDriverService {
    List<Ride> getRides(Integer id);
}
