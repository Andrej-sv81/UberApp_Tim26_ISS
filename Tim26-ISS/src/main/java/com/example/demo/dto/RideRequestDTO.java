package com.example.demo.dto;

import java.util.List;

public class RideRequestDTO
{
    private List<RouteDTO> locations;
    private List<PassengerRideOverDTO> passengers;
    private String vehicleType;
    private boolean babyTransport;
    private boolean petTransport;
}
