package com.example.demo.service.interfaces;

import com.example.demo.model.Route;

import java.util.List;

public interface IRouteService {
    List<Route> getRoutesFromRide(Integer id);

    List<Route> getRoutesFromFavoriteRide(Integer id);
}
