package com.example.demo.dto;

import java.util.List;

public class RideRequestDTO
{
    private List<RouteDTO> locations;
    private List<PassengerRideOverDTO> passengers;
    private String vehicleType;
    private boolean babyTransport;
    private boolean petTransport;

    public RideRequestDTO() {
    }

    public RideRequestDTO(List<RouteDTO> locations, List<PassengerRideOverDTO> passengers, String vehicleType, boolean babyTransport, boolean petTransport) {
        this.locations = locations;
        this.passengers = passengers;
        this.vehicleType = vehicleType;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
    }

    public List<RouteDTO> getLocations() {
        return locations;
    }

    public void setLocations(List<RouteDTO> locations) {
        this.locations = locations;
    }

    public List<PassengerRideOverDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerRideOverDTO> passengers) {
        this.passengers = passengers;
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
        return "RideRequestDTO{" +
                "locations=" + locations +
                ", passengers=" + passengers +
                ", vehicleType='" + vehicleType + '\'' +
                ", babyTransport=" + babyTransport +
                ", petTransport=" + petTransport +
                '}';
    }
}
