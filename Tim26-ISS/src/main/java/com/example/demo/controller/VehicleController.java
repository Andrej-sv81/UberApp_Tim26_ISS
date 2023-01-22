package com.example.demo.controller;

import com.example.demo.dto.LocationDTO;
import com.example.demo.exceptions.UserIdNotMatchingException;
import com.example.demo.model.Location;
import com.example.demo.model.Vehicle;
import com.example.demo.service.LocationService;
import com.example.demo.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;


@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @Autowired
    LocationService locationService;



    //TODO test if cascade save works
    @PreAuthorize("hasAuthority('ROLE_DRIVER') || hasAuthority('ROLE_ADMIN')")
    @PutMapping(value = "/{id}/location", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeLocation(@PathVariable(value = "id", required = true) Integer id,
                                            @Valid @RequestBody LocationDTO request,
                                            Principal userPrincipal) {
        Vehicle vehicle = vehicleService.findOneById(id);
        if(!userPrincipal.getName().equals(vehicle.getDriver().getEmail())){
            throw new UserIdNotMatchingException();
        }
        Location locationOld = vehicle.getLocation();
        if (locationOld != null) {
            locationOld.setAddress(request.getAddress());
            locationOld.setLatitude(request.getLatitude());
            locationOld.setLongitude(request.getLongitude());
            vehicle.setLocation(locationOld);
        } else {
            Location location = new Location(request);
            vehicle.setLocation(location);
        }
        vehicleService.save(vehicle);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
