package com.example.demo.model;

public enum VehicleTypeEnum {
    STANDARD, LUXURIOUS, VAN;

    public static VehicleTypeEnum getType(String type) {
        VehicleTypeEnum returnType = VehicleTypeEnum.STANDARD;
        switch (type) {

            case "STANDARD":
                break;
            case "LUXURIOUS":
                returnType = VehicleTypeEnum.LUXURIOUS;
                break;
            case "VAN":
                returnType = VehicleTypeEnum.VAN;
                break;
        }
        return returnType;
    }
}
