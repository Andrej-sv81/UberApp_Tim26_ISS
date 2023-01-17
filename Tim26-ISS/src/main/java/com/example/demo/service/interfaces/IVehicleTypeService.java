package com.example.demo.service.interfaces;

import com.example.demo.model.VehicleType;
import com.example.demo.model.VehicleTypeEnum;

public interface IVehicleTypeService {
    VehicleType findOneByName(VehicleTypeEnum name);
}
