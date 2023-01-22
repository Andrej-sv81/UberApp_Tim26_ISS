package com.example.demo.dto.ride;

import com.example.demo.dto.RejectionDTO;
import com.example.demo.dto.RouteDTO;
import com.example.demo.dto.driver.DriverRideOverDTO;
import com.example.demo.dto.passenger.PassengerRideOverDTO;
import com.example.demo.model.Ride;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RideResponseDTO {
    private  Integer id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int totalCost;
    private DriverRideOverDTO driver;
    private List<PassengerRideOverDTO> passengers;
    private String estimatedTimeInMinutes;
    private String vehicleType;
    private boolean babyTransport;
    private boolean petTransport;
    private RejectionDTO rejection;
    private List<RouteDTO> locations;
    private String status;

    public RideResponseDTO() {
        this.setRejection(new RejectionDTO());
        this.setLocations(new ArrayList<RouteDTO>());
        this.setPassengers(new ArrayList<PassengerRideOverDTO>());
        this.setDriver(new DriverRideOverDTO());
    }

    public RideResponseDTO(Integer id, LocalDateTime startTime, LocalDateTime endTime, int totalCost, DriverRideOverDTO driver, List<PassengerRideOverDTO> passengers, String estimatedTimeInMinutes, String vehicleType, boolean babyTransport, boolean petTransport, RejectionDTO rejection, List<RouteDTO> locations, String status) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalCost = totalCost;
        this.driver = driver;
        this.passengers = passengers;
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
        this.vehicleType = vehicleType;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
        this.rejection = rejection;
        this.locations = locations;
        this.status = status;
    }

    public RideResponseDTO(Ride newRide) {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public DriverRideOverDTO getDriver() {
        return driver;
    }

    public void setDriver(DriverRideOverDTO driver) {
        this.driver = driver;
    }

    public List<PassengerRideOverDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerRideOverDTO> passengers) {
        this.passengers = passengers;
    }

    public String getEstimatedTimeInMinutes() {
        return estimatedTimeInMinutes;
    }

    public void setEstimatedTimeInMinutes(String estimatedTimeInMinutes) {
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
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

    public RejectionDTO getRejection() {
        return rejection;
    }

    public void setRejection(RejectionDTO rejection) {
        this.rejection = rejection;
    }

    public List<RouteDTO> getLocations() {
        return locations;
    }

    public void setLocations(List<RouteDTO> locations) {
        this.locations = locations;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RideResponseDTO{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", totalCost=" + totalCost +
                ", driver=" + driver +
                ", passengers=" + passengers +
                ", estimatedTimeInMinutes='" + estimatedTimeInMinutes + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", babyTransport=" + babyTransport +
                ", petTransport=" + petTransport +
                ", rejection=" + rejection +
                ", locations=" + locations +
                ", status='" + status + '\'' +
                '}';
    }
}
