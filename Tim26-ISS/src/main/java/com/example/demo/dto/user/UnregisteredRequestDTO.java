package com.example.demo.dto.user;

import com.example.demo.dto.ride.RidePathDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

public class UnregisteredRequestDTO {
    @Valid
    private List<RidePathDTO> locations;
    @Pattern(regexp="^(STANDARD|LUXURIOUS|VAN)$")
    private String vehicleType;
    @NotNull
    private boolean babyTransport;
    @NotNull
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
