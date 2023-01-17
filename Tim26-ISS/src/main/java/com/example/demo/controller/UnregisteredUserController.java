package com.example.demo.controller;

import com.example.demo.dto.ride.RidePathDTO;
import com.example.demo.dto.user.UnregisteredRequestDTO;
import com.example.demo.dto.user.UnregisteredResponseDTO;
import com.example.demo.model.VehicleType;
import com.example.demo.model.VehicleTypeEnum;
import com.example.demo.service.VehicleService;
import com.example.demo.service.VehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/unregisteredUser")
public class UnregisteredUserController {

    @Autowired
    VehicleTypeService vehicleTypeService;

    @PostMapping( value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UnregisteredResponseDTO>  rideAssumption(@RequestBody UnregisteredRequestDTO request) throws Exception{
        RidePathDTO path = request.getLocations().get(0);
        double lat1 = path.getDeparture().getLatitude();
        double lon1 = path.getDeparture().getLongitude();
        double lat2 = path.getDestination().getLatitude();
        double lon2 = path.getDestination().getLongitude();
        double resultDistance = calculateDistance(lat1, lon1, lat2, lon2);

        String typeString = request.getVehicleType();
        boolean babyFlag = request.isBabyTransport();
        boolean petFlag = request.isPetTransport();
        VehicleType type = vehicleTypeService.findOneByName(VehicleTypeEnum.getType(typeString));

        double price = type.getPrice() + resultDistance * 120;
        double minutes = resultDistance/80 * 60;
        UnregisteredResponseDTO responseDTO = new UnregisteredResponseDTO( Math.round(minutes), Math.round(price));
        return  new ResponseEntity<UnregisteredResponseDTO>(responseDTO, HttpStatus.OK);
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2){
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return dist;
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
