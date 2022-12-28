package com.example.demo.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("PASSENGER")
public class Passenger extends User {

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "passenger_rides",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "ride_id"))
    private List<Ride> rides;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Route> routes;

    public Passenger() {
        super();
    }

    public Passenger(String name, String surname, String profilePicture, String telephoneNumber, String email, String address, String password, boolean blocked, boolean active, List<Ride> rides, List<Route> routes) {
        super(name, surname, profilePicture, telephoneNumber, email, address, password, blocked, active);
        this.rides = rides;
        this.routes = routes;
    }

    public List<Ride> getRides() {
        return rides;
    }

    public void setRides(List<Ride> rides) {
        this.rides = rides;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public void addRide(Ride ride){
        rides.add(ride);
        List<Passenger> l = ride.getPassengers();
        l.add(this);
        ride.setPassengers(l);
    }

    public void removeRide(Ride ride){
        rides.remove(ride);
        List<Passenger> l = ride.getPassengers();
        l.remove(this);
        ride.setPassengers(l);
    }

    @Override
    public String toString() {
        return super.toString()+"\nPassenger{" +
                "rides=" + rides +
                ", routes=" + routes +
                '}';
    }
}
