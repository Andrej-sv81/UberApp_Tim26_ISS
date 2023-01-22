package com.example.demo.service;

import com.example.demo.model.Route;
import com.example.demo.repository.RouteRepository;
import com.example.demo.service.interfaces.IRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService implements IRouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Override
    public List<Route> getRoutesFromRide(Integer id) {
        return routeRepository.getRoutesFromRide(id);
    }
}
