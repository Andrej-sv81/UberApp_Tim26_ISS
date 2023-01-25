package com.example.demo.dto;

import com.example.demo.dto.passenger.PassengerRideOverDTO;
import com.example.demo.dto.ride.RidePassengerDTO;
import com.example.demo.model.FavoriteRide;
import com.example.demo.model.Passenger;
import com.example.demo.model.Route;
import com.example.demo.service.PassengerService;
import com.example.demo.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

public class FavoritesRequestDTO {
    @Autowired
    PassengerService passengerService;
    @Autowired
    RouteService routeService;
    @NotBlank
    private String favoriteName;
    @Valid
    private List<RouteDTO> locations;
    @Valid
    private List<RidePassengerDTO> passengers;
    @Pattern(regexp="^(STANDARD|LUXURIOUS|VAN)$")
    private String vehicleType;
    @NotNull
    private boolean babyTransport;
    @NotNull
    private boolean petTransport;

    public FavoritesRequestDTO(){};
    public FavoritesRequestDTO(String favoriteName, List<RouteDTO> locations,
                               List<RidePassengerDTO> passengers, String vehicleType,
                               boolean babyTransport, boolean petTransport) {
        this.favoriteName = favoriteName;
        this.locations = locations;
        this.passengers = passengers;
        this.vehicleType = vehicleType;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
    }

    public PassengerService getPassengerService() {
        return passengerService;
    }

    public void setPassengerService(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    public RouteService getRouteService() {
        return routeService;
    }

    public void setRouteService(RouteService routeService) {
        this.routeService = routeService;
    }

    public String getFavoriteName() {
        return favoriteName;
    }

    public void setFavoriteName(String favoriteName) {
        this.favoriteName = favoriteName;
    }

    public List<RouteDTO> getLocations() {
        return locations;
    }

    public void setLocations(List<RouteDTO> locations) {
        this.locations = locations;
    }

    public List<RidePassengerDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<RidePassengerDTO> passengers) {
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
        return "FavoritesRequestDTO{" +
                "passengerService=" + passengerService +
                ", routeService=" + routeService +
                ", favoriteName='" + favoriteName + '\'' +
                ", locations=" + locations +
                ", passengers=" + passengers +
                ", vehicleType='" + vehicleType + '\'' +
                ", babyTransport=" + babyTransport +
                ", petTransport=" + petTransport +
                '}';
    }
}
