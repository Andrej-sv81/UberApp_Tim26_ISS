package com.example.demo.dto.ride;

import com.example.demo.dto.RouteDTO;
import com.example.demo.dto.passenger.PassengerRideOverDTO;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

public class RideRequestDTO
{
    @Valid
    private List<RouteDTO> locations;
    @Valid
    private List<PassengerRideOverDTO> passengers;
    @Pattern(regexp="^(STANDARD|LUXURIOUS|VAN)$")
    private String vehicleType;
    @NotNull
    private boolean babyTransport;
    @NotNull
    private boolean petTransport;

    @Nullable
    private String scheduledTime;
    public RideRequestDTO() {
    }

    public RideRequestDTO(List<RouteDTO> locations, List<PassengerRideOverDTO> passengers, String vehicleType, boolean babyTransport, boolean petTransport, String scheduledTime) {
        this.locations = locations;
        this.passengers = passengers;
        this.vehicleType = vehicleType;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
        this.scheduledTime = scheduledTime;
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

    public String getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    @Override
    public String toString() {
        return "RideRequestDTO{" +
                "locations=" + locations +
                ", passengers=" + passengers +
                ", vehicleType='" + vehicleType + '\'' +
                ", babyTransport=" + babyTransport +
                ", petTransport=" + petTransport +
                ", scheduledTime='" + scheduledTime + '\'' +
                '}';
    }
}
