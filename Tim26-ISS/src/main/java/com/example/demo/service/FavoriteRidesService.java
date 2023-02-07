package com.example.demo.service;

import com.example.demo.exceptions.FavoriteRideDoesNotExistException;
import com.example.demo.model.FavoriteRide;
import com.example.demo.repository.FavoriteRideRepository;
import com.example.demo.service.interfaces.IFavoriteRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class FavoriteRidesService implements IFavoriteRideService {
    @Autowired
    FavoriteRideRepository favoriteRideRepository;

    @Override
    public int getCount(Integer id) {
        return favoriteRideRepository.getCount(id);
    }

    @Override
    public List<FavoriteRide> getRidesOfUser(Integer id) {
        return favoriteRideRepository.getRidesOfUser(id);
    }

    @Override
    public FavoriteRide findOneById(Integer id) {
        Optional<FavoriteRide> found = favoriteRideRepository.findById(id);
        if (found.isEmpty()) {
            throw new FavoriteRideDoesNotExistException();
        }
        return found.get();
    }

    @Override
    public Integer[] getUserOfRide(Integer id) {
        return favoriteRideRepository.getUserOfRide(id);
    }

    @Override
    public void delete(FavoriteRide ride) {
        favoriteRideRepository.delete(ride);
    }

    @Override
    public void save(FavoriteRide ride) {
        favoriteRideRepository.save(ride);
        favoriteRideRepository.flush();
    }
}
