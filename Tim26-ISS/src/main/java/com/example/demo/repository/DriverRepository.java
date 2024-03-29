package com.example.demo.repository;


import com.example.demo.model.Driver;
import com.example.demo.model.Document;
import com.example.demo.model.Driver;
import com.example.demo.model.Ride;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DriverRepository  extends  UserRepository{

    @Query("select d.rides from Driver d where d.id=?1")
    List<Ride> getRides(Integer id);
    @Query(value = "SELECT * FROM USERS_TABLE WHERE USER_ID = (SELECT DRIVER_ID FROM RIDE WHERE RIDE_ID = ?1)", nativeQuery = true)
    Driver getDriverOfRide(Integer id);
    @Query("select d from Driver d where d.email = ?1")
    Optional<Driver> findByEmail(String mail);


    @Query("select d.documents from Driver d where d.id=?1")
    List<Document> getDocuments(Integer id);

    @Query("select d from Driver d where d.active=true")
    List<Driver> getActiveDrivers();
}
