package com.example.demo.repository;

import com.example.demo.model.Driver;
import com.example.demo.model.Ride;
import com.example.demo.model.WorkingHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkingHourRepository extends JpaRepository<WorkingHour, Integer> {
    List<WorkingHour> getWorkingHourByDriver_Id(Integer id);        // TODO proveri da li ovo moze ovako da radi
    List<WorkingHour> getWorkingHourByDriver(Driver driver);

}
