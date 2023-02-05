package com.example.demo.model;

import com.example.demo.dto.FavoritesRequestDTO;
import com.example.demo.dto.LocationDTO;
import com.example.demo.dto.RouteDTO;
import com.example.demo.dto.ride.RidePassengerDTO;
import com.example.demo.service.PassengerService;
import com.example.demo.service.RouteService;
import com.example.demo.service.VehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FavoriteRide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fav_ride_id", nullable = false)
    private Integer id;
    @Column(name="name")
    private String name;
    @ManyToMany(fetch = FetchType.LAZY)
    @Column(name = "passengers",nullable = false)
    private List<Passenger> passengers;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Route> routes;
    @Column(name="baby_flag", nullable = false)
    private boolean babyFlag;
    @Column(name="pet_flag", nullable = false)
    private boolean petFlag;
    @OneToOne
    private VehicleType vehicleType;


    public FavoriteRide(){
        super();
    }

    public FavoriteRide(String name, List<Passenger> passengers, List<Route> routes,
                        boolean babyFlag, boolean petFlag, VehicleType vehicleType) {
        this.name = name;
        this.passengers = passengers;
        this.routes = routes;
        this.babyFlag = babyFlag;
        this.petFlag = petFlag;
        this.vehicleType = vehicleType;
    }

    public FavoriteRide(FavoritesRequestDTO request, List<Passenger> passengers, List<Route> routes, VehicleType type) {
        this.name = request.getFavoriteName();
        this.routes = routes;
        this.passengers = passengers;
        this.vehicleType = type;
        this.babyFlag = request.isBabyTransport();
        this.petFlag = request.isPetTransport();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public boolean isBabyFlag() {
        return babyFlag;
    }

    public void setBabyFlag(boolean babyFlag) {
        this.babyFlag = babyFlag;
    }

    public boolean isPetFlag() {
        return petFlag;
    }

    public void setPetFlag(boolean petFlag) {
        this.petFlag = petFlag;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Override
    public String toString() {
        return "FavoriteRide{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", passengers=" + passengers +
                ", routes=" + routes +
                ", babyFlag=" + babyFlag +
                ", petFlag=" + petFlag +
                ", vehicleType=" + vehicleType +
                '}';
    }
}

