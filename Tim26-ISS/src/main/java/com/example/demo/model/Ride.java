package com.example.demo.model;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.List;

@Entity
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "start_time", nullable = true)
    private Time startTime;

    @Column(name = "end_time", nullable = false)
    private Time endTime;

    @Column(name = "total_cost", nullable = true)
    private int totalCost;
    @ManyToOne
    @JoinColumn(name = "rides")
    private Driver driver;
    @ManyToMany
    @Column(name = "passengers", nullable = false)
    private List<Passenger> passengers;

    @OneToMany
    private List<Route> routes;

    private Time estimatedTime;
    @OneToMany
    private List<Review> reviews;

    @Enumerated(EnumType.ORDINAL)
    private RideState rideState;

    //TODO
    @ManyToOne
    @JoinColumn(name = "rejection_message_id")
    private RejectionMessage rejectionMessage;

    private boolean panicFlag;

    private boolean babyFlag;

    private boolean petFlag;

    //TODO
    @ManyToOne
    @JoinColumn(name = "vehicle_type_id")
    private VehicleType vehicleType;

    public Ride(){
        super();
    }
    public Ride(Time startTime, Time endTime, int totalCost, Driver driver, List<Passenger> passengers, List<Route> routes,
                Time estimatedTime, List<Review> reviews, RideState rideState, RejectionMessage rejectionMessage,
                boolean panicFlag, boolean babyFlag, boolean petFlag, VehicleType vehicleType) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalCost = totalCost;
        this.driver = driver;
        this.passengers = passengers;
        this.routes = routes;
        this.estimatedTime = estimatedTime;
        this.reviews = reviews;
        this.rideState = rideState;
        this.rejectionMessage = rejectionMessage;
        this.panicFlag = panicFlag;
        this.babyFlag = babyFlag;
        this.petFlag = petFlag;
        this.vehicleType = vehicleType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
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

    public Time getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Time estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public RideState getRideState() {
        return rideState;
    }

    public void setRideState(RideState rideState) {
        this.rideState = rideState;
    }

    public RejectionMessage getRejectionMessage() {
        return rejectionMessage;
    }

    public void setRejectionMessage(RejectionMessage rejectionMessage) {
        this.rejectionMessage = rejectionMessage;
    }

    public boolean isPanicFlag() {
        return panicFlag;
    }

    public void setPanicFlag(boolean panicFlag) {
        this.panicFlag = panicFlag;
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
        return "Ride{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", totalCost=" + totalCost +
                ", driver=" + driver +
                ", passengers=" + passengers +
                ", routes=" + routes +
                ", estimatedTime=" + estimatedTime +
                ", reviews=" + reviews +
                ", rideState=" + rideState +
                ", rejectionMessage=" + rejectionMessage +
                ", panicFlag=" + panicFlag +
                ", babyFlag=" + babyFlag +
                ", petFlag=" + petFlag +
                ", vehicleType=" + vehicleType +
                '}';
    }
}
