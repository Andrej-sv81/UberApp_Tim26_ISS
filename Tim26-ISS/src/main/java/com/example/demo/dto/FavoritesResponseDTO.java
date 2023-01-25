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

public class FavoritesResponseDTO {

    private Integer id;
    private String favoriteName;

    private List<RouteDTO> locations;

    private List<RidePassengerDTO> passengers;

    private String vehicleType;

    private boolean babyTransport;

    private boolean petTransport;

    public FavoritesResponseDTO(){};
    public FavoritesResponseDTO(Integer id, String favoriteName, List<RouteDTO> locations,
                               List<RidePassengerDTO> passengers, String vehicleType,
                               boolean babyTransport, boolean petTransport) {
        this.id = id;
        this.favoriteName = favoriteName;
        this.locations = locations;
        this.passengers = passengers;
        this.vehicleType = vehicleType;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
    }
    public FavoritesResponseDTO(FavoriteRide ride,
                                PassengerService passengerService,
                                RouteService routeService){
        this.id = ride.getId();
        this.favoriteName = ride.getName();
        List<RidePassengerDTO> passengers = new ArrayList<RidePassengerDTO>();
        List<Passenger> ridePassengers = passengerService.getPassengersOfFavoriteRide(ride.getId());
        for(Passenger p: ridePassengers){
            passengers.add(new RidePassengerDTO(p.getId(), p.getEmail()));
        }

        List<RouteDTO> routes = new ArrayList<RouteDTO>();
        List<Route> rideRoutes = routeService.getRoutesFromFavoriteRide(ride.getId());
        for(Route r: rideRoutes){
            routes.add(new RouteDTO(new LocationDTO(r.getStartLocation()), new LocationDTO(r.getDestination())));
        }
        this.locations = routes;
        this.passengers = passengers;
        this.vehicleType = ride.getVehicleType().getName().toString();
        this.babyTransport = ride.isBabyFlag();
        this.petTransport = ride.isPetFlag();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        return "FavoritesResponseDTO{" +
                ", id=" + id +
                ", favoriteName='" + favoriteName + '\'' +
                ", locations=" + locations +
                ", passengers=" + passengers +
                ", vehicleType='" + vehicleType + '\'' +
                ", babyTransport=" + babyTransport +
                ", petTransport=" + petTransport +
                '}';
    }
}
