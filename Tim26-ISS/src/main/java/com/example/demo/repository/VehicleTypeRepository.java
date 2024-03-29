package com.example.demo.repository;

import com.example.demo.model.VehicleType;
import com.example.demo.model.VehicleTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Integer> {
    VehicleType findOneByName(VehicleTypeEnum name);
}
