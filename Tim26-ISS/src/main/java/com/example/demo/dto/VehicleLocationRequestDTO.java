package com.example.demo.dto;

import javax.validation.constraints.NotNull;

public class VehicleLocationRequestDTO {

    @NotNull
    private String address;
    @NotNull
    private double latitude;
    @NotNull
    private double longitude;

    public VehicleLocationRequestDTO(){};
    public VehicleLocationRequestDTO(String address, double latitude, double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "VehicleLocationRequestDTO{" +
                "address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
