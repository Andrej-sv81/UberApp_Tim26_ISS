package com.example.demo.repository;

import com.example.demo.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Integer> {
    @Query(value = "SELECT * FROM ROUTE WHERE ROUTE_ID IN (SELECT ROUTES_ROUTE_ID FROM RIDE_ROUTES WHERE RIDE_RIDE_ID = ?1)", nativeQuery = true)
    List<Route> getRoutesFromRide(Integer id);
    @Query(value = "SELECT * FROM ROUTE WHERE ROUTE_ID IN (SELECT ROUTES_ROUTE_ID FROM FAVORITE_RIDE_ROUTES WHERE FAVORITE_RIDE_FAV_RIDE_ID = ?1)", nativeQuery = true)
    List<Route> getRoutesFromFavoriteRide(Integer id);
}
