package com.example.demo.service;

import com.example.demo.model.VehicleType;
import com.example.demo.model.VehicleTypeEnum;
import com.example.demo.repository.VehicleTypeRepository;
import com.example.demo.service.interfaces.IVehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleTypeService implements IVehicleTypeService {

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;


    @Override
    public VehicleType findOneByName(VehicleTypeEnum name) {
        return vehicleTypeRepository.findOneByName(name);
    }

}
