package com.example.demo.model;

import javax.persistence.*;

import java.sql.Time;
import java.util.List;

@Entity
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ride_id", nullable = false)
    private Integer id;
    @Column(name = "start_time", nullable = true)
    private Time startTime;
    @Column(name = "end_time", nullable = true)
    private Time endTime;
    @Column(name = "total_cost", nullable = true)
    private Integer totalCost;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;
    @ManyToMany(fetch = FetchType.EAGER)
    @Column(name = "passengers",nullable = false)
    private List<Passenger> passengers;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Route> routes;
    @Column(name="estimated_time", nullable = true)
    private Time estimatedTime;
    @OneToMany(mappedBy = "ride",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviews;
    @Enumerated(EnumType.ORDINAL)
    private RideState rideState;
    @OneToOne
    private RejectionMessage rejectionMessage;

    @Column(name="panic_flag", nullable = false)
    private boolean panicFlag;
    @Column(name="baby_flag", nullable = false)
    private boolean babyFlag;
    @Column(name="pet_flag", nullable = false)
    private boolean petFlag;
    @OneToOne
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
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

    public void addReview(Review review) {
        reviews.add(review);
        review.setRide(this);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
        review.setRide(null);
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
