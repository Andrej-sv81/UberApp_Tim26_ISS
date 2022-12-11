package com.example.demo.dto;

import java.util.List;

public class UnregisteredRequestDTO {

    private List<RidePathDTO> locations;
    private String vehicleType;
    private boolean babyTransport;
    private boolean petTransport;

    public UnregisteredRequestDTO(){};

    public UnregisteredRequestDTO(List<RidePathDTO> locations, String vehicleType, boolean babyTransport, boolean petTransport) {
        this.locations = locations;
        this.vehicleType = vehicleType;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
    }

    public List<RidePathDTO> getLocations() {
        return locations;
    }

    public void setLocations(List<RidePathDTO> locations) {
        this.locations = locations;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public boolean isBabyTransport() {
        return babyTransport;
    }

    public void setBabyTransport(boolean babyTransport) {
        this.babyTransport = babyTransport;
    }

    public boolean isPetTransport() {
        return petTransport;
    }

    public void setPetTransport(boolean petTransport) {
        this.petTransport = petTransport;
    }

    @Override
    public String toString() {
        return "UnregisteredResponseDTO{" +
                "locations=" + locations +
                ", vehicleType='" + vehicleType + '\'' +
                ", babyTransport=" + babyTransport +
                ", petTransport=" + petTransport +
                '}';
    }
}
