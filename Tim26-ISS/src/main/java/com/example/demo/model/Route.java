package com.example.demo.model;

import javax.persistence.*;
import javax.persistence.GeneratedValue;


@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne
    private Location startLocation;

    @OneToOne
    private Location destination;

    @Column(name="distance_in_km", nullable = false)
    private int distanceInKm;

    public Route(){
        super();
    }

    public Route(Location startLocation, Location destination, int distanceInKm) {
        this.startLocation = startLocation;
        this.destination = destination;
        this.distanceInKm = distanceInKm;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public int getDistanceInKm() {
        return distanceInKm;
    }

    public void setDistanceInKm(int distanceInKm) {
        this.distanceInKm = distanceInKm;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", startLocation='" + startLocation + '\'' +
                ", destination='" + destination + '\'' +
                ", distanceInKm=" + distanceInKm +
                '}';
    }
}
