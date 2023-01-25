package com.example.demo.dto.ride;

import com.example.demo.dto.RejectionDTO;
import com.example.demo.model.Ride;

import java.util.Date;
import java.util.List;

public class RideDTO {
    private Integer id;
    private String startTime;
    private String endTime;
    private Integer totalCost;
    private RideDriverDTO driver;
    private List<RidePassengerDTO> passengers;
    private Integer estimatedTimeInMinutes;
    private String vehicleType;
    private boolean babyTransport;
    private boolean petTransport;
    private RejectionDTO rejection;
    private List<RidePathDTO> locations;
    private String scheduledTime;

    public RideDTO(){};
    public RideDTO(Integer id, String startTime, String endTime, Integer totalCost, RideDriverDTO driver, List<RidePassengerDTO> passengers, Integer estimatedTimeInMinutes, String vehicleType, boolean babyTransport, boolean petTransport, RejectionDTO rejection, List<RidePathDTO> locations, Date scheduledTime) {
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
        this.scheduledTime = scheduledTime.toString();
    }
    public RideDTO(Ride ride, RideDriverDTO rideDriverDTO, List<RidePassengerDTO> passengers, RejectionDTO rejection, List<RidePathDTO> locations){
        this.id = ride.getId();
        this.startTime = ride.getStartTime() == null ? "" : ride.getStartTime().toString();
        this.endTime = ride.getEndTime() == null ? "" : ride.getEndTime().toString();
        this.totalCost = ride.getTotalCost();
        this.driver = rideDriverDTO;
        this.passengers = passengers;
        this.estimatedTimeInMinutes = ride.getEstimatedTime();
        this.vehicleType = ride.getVehicleType().getName().toString();
        this.babyTransport = ride.isBabyFlag();
        this.petTransport = ride.isPetFlag();
        this.rejection = rejection;
        this.locations = locations;
        this.scheduledTime = ride.getScheduledTime() == null ? "" : ride.getScheduledTime().toString();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    public RideDriverDTO getDriver() {
        return driver;
    }

    public void setDriver(RideDriverDTO driver) {
        this.driver = driver;
    }

    public List<RidePassengerDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<RidePassengerDTO> passengers) {
        this.passengers = passengers;
    }

    public Integer getEstimatedTimeInMinutes() {
        return estimatedTimeInMinutes;
    }

    public void setEstimatedTimeInMinutes(Integer estimatedTimeInMinutes) {
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

    public List<RidePathDTO> getLocations() {
        return locations;
    }

    public void setLocations(List<RidePathDTO> locations) {
        this.locations = locations;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    @Override
    public String toString() {
        return "RideDTO{" +
                "id=" + id +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", totalCost=" + totalCost +
                ", driver=" + driver +
                ", passengers=" + passengers +
                ", estimatedTimeInMinutes=" + estimatedTimeInMinutes +
                ", vehicleType='" + vehicleType + '\'' +
                ", babyTransport=" + babyTransport +
                ", petTransport=" + petTransport +
                ", rejection=" + rejection +
                ", locations=" + locations +
                '}';
    }
}
