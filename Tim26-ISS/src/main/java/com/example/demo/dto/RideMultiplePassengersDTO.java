package com.example.demo.dto;

import java.util.List;

public class RideMultiplePassengersDTO {
    private List<RidePassengerDTO> passengers;

    public RideMultiplePassengersDTO(){};
    public RideMultiplePassengersDTO(List<RidePassengerDTO> passengers) {
        this.passengers = passengers;
    }

    public List<RidePassengerDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<RidePassengerDTO> passengers) {
        this.passengers = passengers;
    }

    @Override
    public String toString() {
        return "RideMultiplePassengersDTO{" +
                "passengers=" + passengers +
                '}';
    }
}
