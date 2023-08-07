package com.example.demo.service.interfaces;

import com.example.demo.dto.ExplanationDTO;
import com.example.demo.dto.ride.RideRequestDTO;
import com.example.demo.dto.ride.RideResponseDTO;
import com.example.demo.model.Driver;
import com.example.demo.model.Review;
import com.example.demo.model.Ride;
import com.example.demo.model.Role;

import java.security.Principal;
import java.util.List;

public interface IRideService {
 Ride findOneById(Integer id);
 Driver getDriverOfRide(int rideId);
 void save(Ride ride);
 List<Review> getReviews(Integer id);
 List<Ride> getRides(Integer id, Integer page, Integer size, String sort, String from, String to);
 List<Ride> getRidesPassenger(Integer id, Integer page, Integer size, String sort, String from, String to);
 List<Ride> findPendingRides(Integer id);
 Ride findActiveRideForDriver(Integer driverId);
 Ride findActiveRideForPassenger(Integer passengerId);
 boolean validateRideRequestDTO(RideRequestDTO ride);
 boolean checkForPendingRide(String name);
 RideResponseDTO createRide(RideRequestDTO ride, String name);
 Object[] findSuitableDriver(Ride ride);
 RideResponseDTO acceptRide(Integer id, Principal principal);
 RideResponseDTO decline(Integer id, ExplanationDTO explanationDTO, Principal userPrincipal);
 RideResponseDTO endRide(Integer id, Principal principal);
 RideResponseDTO startRide(Integer id, Principal userPrincipal);
 RideResponseDTO cancelRidePassenger(Integer id,Principal principal);
 RideResponseDTO addFields(RideResponseDTO rideResponseDTO);
}
