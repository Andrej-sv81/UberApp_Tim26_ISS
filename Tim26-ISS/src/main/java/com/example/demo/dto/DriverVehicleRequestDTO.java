package com.example.demo.dto;

import com.example.demo.model.Location;
import com.example.demo.model.Vehicle;

public class DriverVehicleRequestDTO {
    private Vehicle.vehicleType vehicleType;
    private String model;
    private String licenseNumber;
    private Location currentLocation;
    private int passengerSeats;
    private boolean babyTransport;
    private boolean petTransport;

    public DriverVehicleRequestDTO() {
    }

    public DriverVehicleRequestDTO(Vehicle.vehicleType vehicleType, String model, String licenseNumber, Location currentLocation, int passengerSeats, boolean babyTransport, boolean petTransport) {
        this.vehicleType = vehicleType;
        this.model = model;
        this.licenseNumber = licenseNumber;
        this.currentLocation = currentLocation;
        this.passengerSeats = passengerSeats;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
    }

    public Vehicle.vehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Vehicle.vehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public int getPassengerSeats() {
        return passengerSeats;
    }

    public void setPassengerSeats(int passengerSeats) {
        this.passengerSeats = passengerSeats;
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
        return "DriverVehicleRequestDTO{" +
                "vehicleType=" + vehicleType +
                ", model='" + model + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", currentLocation=" + currentLocation +
                ", passengerSeats=" + passengerSeats +
                ", babyTransport=" + babyTransport +
                ", petTransport=" + petTransport +
                '}';
    }
}
