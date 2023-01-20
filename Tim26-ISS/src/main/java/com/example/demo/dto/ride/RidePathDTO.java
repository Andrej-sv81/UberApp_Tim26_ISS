package com.example.demo.dto.ride;

import com.example.demo.dto.LocationDTO;
import com.example.demo.model.Route;

import javax.validation.Valid;

public class RidePathDTO {
    @Valid
    private LocationDTO departure;
    @Valid
    private LocationDTO destination;

    public RidePathDTO(){};

    public RidePathDTO(LocationDTO departure, LocationDTO destination) {
        this.departure = departure;
        this.destination = destination;
    }
    public RidePathDTO(Route route){
        this.departure = new LocationDTO(route.getStartLocation());
        this.destination = new LocationDTO(route.getDestination());
    }

    public LocationDTO getDeparture() {
        return departure;
    }

    public void setDeparture(LocationDTO departure) {
        this.departure = departure;
    }

    public LocationDTO getDestination() {
        return destination;
    }

    public void setDestination(LocationDTO destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "RidePathDTO{" +
                "departure=" + departure +
                ", destination=" + destination +
                '}';
    }
}
