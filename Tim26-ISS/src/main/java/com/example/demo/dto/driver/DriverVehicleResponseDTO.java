package com.example.demo.dto.driver;

import com.example.demo.model.Location;
import com.example.demo.model.Vehicle;
import com.example.demo.model.VehicleType;

public class DriverVehicleResponseDTO {
    private Integer id;
    private int driverId;
    private String vehicleType;
    private String model;
    private String licenseNumber;
    private Location currentLocation;
    private int passengerSeats;
    private boolean babyTransport;
    private boolean petTransport;

    public DriverVehicleResponseDTO() {
    }

    public DriverVehicleResponseDTO(Integer id, int driverId, String vehicleType, String model, String licenseNumber, Location currentLocation, int passengerSeats, boolean babyTransport, boolean petTransport) {
        this.id = id;
        this.driverId = driverId;
        this.vehicleType = vehicleType;
        this.model = model;
        this.licenseNumber = licenseNumber;
        this.currentLocation = currentLocation;
        this.passengerSeats = passengerSeats;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
    }

    public DriverVehicleResponseDTO(Vehicle vehicle) {
        this.id = vehicle.getId();
        this.driverId = vehicle.getDriver().getId();
        this.vehicleType = String.valueOf(vehicle.getVehicleType().getName());
        this.model = vehicle.getVehicleModel();
        this.licenseNumber = vehicle.getRegistrationPlates();
        this.currentLocation = vehicle.getLocation();
        this.passengerSeats = vehicle.getNumberOfSeats();
        this.babyTransport = vehicle.isBabyFlag();
        this.petTransport = vehicle.isPetFlag();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
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
        return "DriverVehicleResponseDTO{" +
                "id=" + id +
                ", driverId=" + driverId +
                ", vehicleType=" + vehicleType +
                ", model='" + model + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", currentLocation=" + currentLocation +
                ", passengerSeats=" + passengerSeats +
                ", babyTransport=" + babyTransport +
                ", petTransport=" + petTransport +
                '}';
    }
}
