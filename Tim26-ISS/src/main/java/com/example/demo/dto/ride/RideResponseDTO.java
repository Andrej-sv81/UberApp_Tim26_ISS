package com.example.demo.dto.ride;

import com.example.demo.dto.LocationDTO;
import com.example.demo.dto.RejectionDTO;
import com.example.demo.dto.RouteDTO;
import com.example.demo.dto.driver.DriverRideOverDTO;
import com.example.demo.dto.passenger.PassengerRideOverDTO;
import com.example.demo.model.Passenger;
import com.example.demo.model.Ride;
import com.example.demo.model.Route;
import com.example.demo.service.PassengerService;
import com.example.demo.service.RejectionMessageService;
import com.example.demo.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RideResponseDTO {
    private Integer id;
    private String startTime;
    private String endTime;
    private int totalCost;
    private DriverRideOverDTO driver;
    private List<PassengerRideOverDTO> passengers;
    private Integer estimatedTimeInMinutes;
    private String vehicleType;
    private boolean babyTransport;
    private boolean petTransport;
    private RejectionDTO rejection;
    private List<RouteDTO> locations;
    private String status;

    String scheduledTime;

    public RideResponseDTO() {
        this.setRejection(new RejectionDTO());
        this.setLocations(new ArrayList<RouteDTO>());
        this.setPassengers(new ArrayList<PassengerRideOverDTO>());
        this.setDriver(new DriverRideOverDTO());
    }

    public RideResponseDTO(Integer id, String startTime, String endTime,
                           int totalCost, DriverRideOverDTO driver, List<PassengerRideOverDTO> passengers,
                           Integer estimatedTimeInMinutes, String vehicleType, boolean babyTransport,
                           boolean petTransport, RejectionDTO rejection, List<RouteDTO> locations,
                           String status, String scheduledTime) {
        this.id = id;
        this.startTime = startTime.toString();
        this.endTime = endTime.toString();
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
        this.scheduledTime = scheduledTime;
    }

    public RideResponseDTO(Ride newRide, List<PassengerRideOverDTO> passengers, List<RouteDTO> route) {
        this.id = newRide.getId();
        this.startTime = "";
        this.endTime = "";
        this.totalCost = newRide.getTotalCost();
        this.driver = null;
        this.passengers = passengers;
        this.estimatedTimeInMinutes = newRide.getEstimatedTime();
        this.vehicleType = newRide.getVehicleType().getName().toString();
        this.babyTransport = newRide.isBabyFlag();
        this.petTransport = newRide.isPetFlag();
        this.rejection = null;
        this.locations = route;
        this.status = newRide.getRideState().toString();
        this.scheduledTime = newRide.getScheduledTime().toString();
    }

    public RideResponseDTO(Ride ride){
        DriverRideOverDTO driverDTO = new DriverRideOverDTO(ride.getDriver().getId(), ride.getDriver().getEmail());
        //TODO putnici,odbijanje,lokacije
        this.id = ride.getId();
        this.startTime = ride.getStartTime().toString();
        this.endTime = ride.getEndTime().toString();
        this.totalCost = ride.getTotalCost();
        this.driver = driverDTO;
        this.estimatedTimeInMinutes = ride.getEstimatedTime();
        this.vehicleType = ride.getVehicleType().getName().toString();
        this.babyTransport = ride.isBabyFlag();
        this.petTransport = ride.isPetFlag();
        this.status = ride.getRideState().toString();
        this.scheduledTime = ride.getScheduledTime() == null ? "" : ride.getScheduledTime().toString();
        this.scheduledTime = ride.getScheduledTime() == null ? "" : ride.getScheduledTime().toString();;

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

    public String getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    @Override
    public String toString() {
        return "RideResponseDTO{" +
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
                ", status='" + status + '\'' +
                ", scheduledTime='" + scheduledTime + '\'' +
                '}';
    }
}
