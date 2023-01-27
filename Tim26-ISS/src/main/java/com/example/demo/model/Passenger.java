package com.example.demo.model;

import com.example.demo.dto.passenger.PassengerRequestDTO;

import javax.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("PASSENGER")
public class Passenger extends User {

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "passenger_rides",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "ride_id"))
    private List<Ride> rides;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "passenger_favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "fav_ride_id"))
    private List<FavoriteRide> routes;

    public Passenger() {
        super();
    }

    public Passenger(String name, String surname, String profilePicture, String telephoneNumber, String email, String address, String password, boolean blocked, boolean active, List<Ride> rides, List<FavoriteRide> routes) {
        super(name, surname, profilePicture, telephoneNumber, email, address, password, blocked, active);
        this.rides = rides;
        this.routes = routes;
    }

    public Passenger(PassengerRequestDTO passenger) {
        this.setName(passenger.getName());
        this.setSurname(passenger.getSurname());
        this.setProfilePicture(passenger.getProfilePicture());
        this.setEmail(passenger.getEmail());
        this.setAddress(passenger.getAddress());
        this.setPassword(passenger.getPassword());
        this.setTelephoneNumber(passenger.getTelephoneNumber());
        this.setActive(false);
    }

    public List<Ride> getRides() {
        return rides;
    }

    public void setRides(List<Ride> rides) {
        this.rides = rides;
    }

    public List<FavoriteRide> getRoutes() {
        return routes;
    }

    public void setRoutes(List<FavoriteRide> routes) {
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
                ", routes=" +  +
                '}';
    }

    public Passenger updatePassenger(PassengerRequestDTO edited){
        setName(edited.getName());
        setSurname(edited.getSurname());
        setProfilePicture(edited.getProfilePicture());
        setTelephoneNumber(edited.getTelephoneNumber());
        setEmail(edited.getEmail());
        setAddress(edited.getAddress());
        return this;
    }
}
