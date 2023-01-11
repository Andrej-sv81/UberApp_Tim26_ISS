package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="start_location", nullable = false)
    private String startLocation;

    @Column(name="destination", nullable = false)
    private String destination;

    @Column(name="distance_in_km", nullable = false)
    private int distanceInKm;

    public Route(){
        super();
    }

    public Route(String startLocation, String destination, int distanceInKm) {
        this.startLocation = startLocation;
        this.destination = destination;
        this.distanceInKm = distanceInKm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
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
