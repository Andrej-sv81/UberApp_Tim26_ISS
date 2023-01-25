package com.example.demo.repository;


import com.example.demo.model.Passenger;
import com.example.demo.model.Ride;
import com.example.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PassengerRepository extends UserRepository{
    @Query("select p.rides from Passenger p where p.id=?1")
    List<Ride> getRides(Integer id);

    Optional<User> findByEmail(String mail);
    @Query(value = "SELECT * FROM USERS_TABLE WHERE USER_ID IN (SELECT USER_ID FROM PASSENGER_RIDES WHERE RIDE_ID = ?1)", nativeQuery = true)
    List<Passenger> getPassengerOfRide(Integer id);
    @Query(value = "SELECT * FROM USERS_TABLE WHERE USER_ID IN (SELECT PASSENGERS_USER_ID FROM FAVORITE_RIDE_PASSENGERS WHERE FAVORITE_RIDE_FAV_RIDE_ID = ?1)", nativeQuery = true)
    List<Passenger> getPassengerOfFavoriteRide(Integer id);
}
