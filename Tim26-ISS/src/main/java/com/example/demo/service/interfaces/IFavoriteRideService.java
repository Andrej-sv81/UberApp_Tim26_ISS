package com.example.demo.service.interfaces;

import com.example.demo.model.FavoriteRide;
import com.example.demo.model.User;

import java.util.List;

public interface IFavoriteRideService {
    int getCount(Integer id);

    List<FavoriteRide> getRidesOfUser(Integer id);

    FavoriteRide findOneById(Integer id);

    Integer getUserOfRide(Integer id);

    void delete(FavoriteRide ride);

    void save(FavoriteRide ride);
}
