package com.example.demo.dto.ride;

import com.example.demo.dto.ExplanationDTO;
import com.example.demo.dto.LocationDTO;
import com.example.demo.dto.RejectionDTO;
import com.example.demo.dto.RouteDTO;
import com.example.demo.dto.driver.DriverRideOverDTO;
import com.example.demo.dto.passenger.PassengerRideOverDTO;
import com.example.demo.dto.user.UserResponseDTO;
import com.example.demo.model.Panic;
import com.example.demo.model.Passenger;
import com.example.demo.model.Ride;
import com.example.demo.model.Route;
import com.example.demo.service.PanicService;
import com.example.demo.service.PassengerService;
import com.example.demo.service.RejectionMessageService;
import com.example.demo.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RidePanicResponseDTO {

   private Integer id;
   private UserResponseDTO user;
   private RideDTO ride;
   private String time;
   private String reason;

    public RidePanicResponseDTO() {
    }

    public RidePanicResponseDTO(Integer id, UserResponseDTO user, RideDTO ride, LocalDateTime time, String reason) {
        this.id = id;
        this.user = user;
        this.ride = ride;
        this.time = time.toString();
        this.reason = reason;
    }

    public RidePanicResponseDTO(Panic panic,
                                RejectionMessageService rejectionMessageService,
                                RouteService routeService,
                                PassengerService passengerService) {
        this.id = panic.getId();
        this.user = new UserResponseDTO(panic.getUser());
        RideDriverDTO driver = new RideDriverDTO(panic.getRide().getDriver().getId(), panic.getRide().getDriver().getEmail());
        RejectionDTO rejection = new RejectionDTO(rejectionMessageService.getMessageFromRide(panic.getRide().getId()));

        List<RidePassengerDTO> passengers = new ArrayList<RidePassengerDTO>();
        List<Passenger> ridePassengers = passengerService.getPassengersOfRide(panic.getRide().getId());
        for(Passenger p: ridePassengers){
            passengers.add(new RidePassengerDTO(p.getId(), p.getEmail()));
        }

        List<RidePathDTO> routes = new ArrayList<RidePathDTO>();
        List<Route> rideRoutes = routeService.getRoutesFromRide(panic.getRide().getId());
        for(Route r: rideRoutes){
            routes.add(new RidePathDTO(new LocationDTO(r.getStartLocation()), new LocationDTO(r.getDestination())));
        }

        this.ride = new RideDTO(panic.getRide(), driver, passengers, rejection, routes);
        this.time = panic.getTime().toString();
        this.reason = panic.getPanicMessage();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    public RideDTO getRide() {
        return ride;
    }

    public void setRide(RideDTO ride) {
        this.ride = ride;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String tine) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "RidePanicResponseDTO{" +
                "id=" + id +
                ", user=" + user +
                ", ride=" + ride +
                ", tine=" + time +
                ", reason='" + reason + '\'' +
                '}';
    }
}
