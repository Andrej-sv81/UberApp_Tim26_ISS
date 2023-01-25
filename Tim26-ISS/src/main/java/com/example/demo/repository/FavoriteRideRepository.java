package com.example.demo.repository;

import com.example.demo.model.FavoriteRide;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavoriteRideRepository extends JpaRepository<FavoriteRide, Integer> {
    @Query(value = "SELECT COUNT(*) FROM FAVORITE_RIDE_PASSENGERS WHERE PASSENGERS_USER_ID=?1", nativeQuery = true)
    int getCount(Integer id);
    @Query(value = "SELECT * FROM FAVORITE_RIDE WHERE FAV_RIDE_ID IN (SELECT FAVORITE_RIDE_FAV_RIDE_ID FROM FAVORITE_RIDE_PASSENGERS WHERE PASSENGERS_USER_ID = ?1)", nativeQuery = true)
    List<FavoriteRide> getRidesOfUser(Integer id);

    @Query(value = "SELECT PASSENGERS_USER_ID FROM FAVORITE_RIDE_PASSENGERS WHERE FAVORITE_RIDE_FAV_RIDE_ID = ?1", nativeQuery = true)
    Integer getUserOfRide(Integer id);
}
