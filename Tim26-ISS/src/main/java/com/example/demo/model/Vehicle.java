package com.example.demo.model;


import com.example.demo.dto.driver.DriverVehicleRequestDTO;

import javax.persistence.*;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @OneToOne(cascade = {CascadeType.REFRESH}, mappedBy = "vehicle", fetch = FetchType.LAZY)
    private Driver driver;
    @Column(name="vehicle_model", nullable = false)
    private String vehicleModel;
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicleTypeId")
    private VehicleType vehicleType;
    @Column(name="registration_plates", nullable = false)
    private String registrationPlates;
    @Column(name="number_of_seats", nullable = false)
    private int numberOfSeats;
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "currentLocationId")
    private Location location;
    @Column(name="baby_flag", nullable = false)
    private boolean babyFlag;
    @Column(name="pet_flag", nullable = false)
    private boolean petFlag;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviews;

    public Vehicle(){
        super();
    }
    public Vehicle(Driver driver, String vehicleModel, VehicleType vehicleType, String registrationPlates, int numberOfSeats, Location location,
                   boolean babyFlag, boolean petFlag, List<Review> reviews) {
        this.driver = driver;
        this.vehicleModel = vehicleModel;
        this.vehicleType = vehicleType;
        this.registrationPlates = registrationPlates;
        this.numberOfSeats = numberOfSeats;
        this.location = location;
        this.babyFlag = babyFlag;
        this.petFlag = petFlag;
        this.reviews = reviews;
    }

    public Vehicle(DriverVehicleRequestDTO addVehicle) {
        this.vehicleModel = addVehicle.getModel();
        //this.vehicleType = addVehicle.getVehicleType();
        this.registrationPlates = addVehicle.getLicenseNumber();
        this.numberOfSeats = addVehicle.getPassengerSeats();
        this.location = addVehicle.getCurrentLocation();
        this.babyFlag = addVehicle.isBabyTransport();
        this.petFlag = addVehicle.isPetTransport();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getRegistrationPlates() {
        return registrationPlates;
    }

    public void setRegistrationPlates(String registrationPlates) {
        this.registrationPlates = registrationPlates;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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

    @Transactional()
    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", driver=" + driver +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", vehicleType=" + vehicleType +
                ", registrationPlates='" + registrationPlates + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", location=" + location +
                ", babyFlag=" + babyFlag +
                ", petFlag=" + petFlag +
                ", reviews=" + reviews +
                '}';
    }

    public void updateVehicle(DriverVehicleRequestDTO newData) {
        this.setVehicleModel(newData.getModel());
        this.setRegistrationPlates(newData.getLicenseNumber());
        this.setLocation(newData.getCurrentLocation());
        this.setNumberOfSeats(newData.getPassengerSeats());
        this.setBabyFlag(newData.isBabyTransport());
        this.setPetFlag(newData.isPetTransport());
    }
}
