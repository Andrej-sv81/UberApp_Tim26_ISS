package com.example.demo.dto;

import ch.qos.logback.core.joran.sanity.Pair;

import java.util.HashMap;
import java.util.List;

public class MultipleLocationsDTO {

    private List<RidePathDTO> locations;

    public MultipleLocationsDTO(){};

    public MultipleLocationsDTO(List<RidePathDTO> locations) {
        this.locations = locations;
    }

    public List<RidePathDTO> getLocations() {
        return locations;
    }

    public void setLocations(List<RidePathDTO> locations) {
        this.locations = locations;
    }

    @Override
    public String toString() {
        return "MultipleLocationsDTO{" +
                "locations=" + locations +
                '}';
    }
}
