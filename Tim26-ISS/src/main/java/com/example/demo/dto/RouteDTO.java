package com.example.demo.dto;

import javax.validation.Valid;

public class RouteDTO {
    @Valid
    private LocationDTO departure;
    @Valid
    private LocationDTO destination;

    public RouteDTO() {
    }

    public RouteDTO(LocationDTO departure, LocationDTO destination) {
        this.departure = departure;
        this.destination = destination;
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
}
