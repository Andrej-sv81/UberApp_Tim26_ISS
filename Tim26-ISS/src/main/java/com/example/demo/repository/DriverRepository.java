package com.example.demo.repository;

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

}
