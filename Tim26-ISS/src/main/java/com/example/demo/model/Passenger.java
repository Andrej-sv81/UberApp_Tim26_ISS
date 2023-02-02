package com.example.demo.model;

import com.example.demo.dto.passenger.PassengerRequestDTO;
import com.example.demo.dto.passenger.PassengerUpdateRequestDTO;

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
        this.setProfilePicture(passenger.getProfilePicture().equals("") ? null: passenger.getProfilePicture());
        this.setEmail(passenger.getEmail());
        this.setAddress(passenger.getAddress());
        this.setPassword(passenger.getPassword());
        this.setTelephoneNumber(passenger.getTelephoneNumber());
        this.setActive(false);
        this.setRole(Role.PASSENGER.toString());
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

    public Passenger updatePassenger(PassengerUpdateRequestDTO edited){
        if(!edited.getName().equals("")){
            setName(edited.getName());
        }
        if(!edited.getSurname().equals("")){
            setSurname(edited.getSurname());
        }
        if(!edited.getProfilePicture().equals("")){
            setProfilePicture(edited.getProfilePicture());
        }
        if(!edited.getTelephoneNumber().equals("")){
            setTelephoneNumber(edited.getTelephoneNumber());
        }
        if(!edited.getEmail().equals("")){
            setEmail(edited.getEmail());
        }
        if(!edited.getAddress().equals("")){
            setAddress(edited.getAddress());
        }
        return this;
    }
}
