package com.example.demo.controller;

import com.example.demo.dto.HttpStatusMessageDTO;
import com.example.demo.dto.VehicleLocationRequestDTO;
import com.example.demo.model.Location;
import com.example.demo.model.User;
import com.example.demo.model.Vehicle;
import com.example.demo.service.LocationService;
import com.example.demo.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @Autowired
    LocationService locationService;

    @PutMapping(value="/{id}/location", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeLocation(@PathVariable(value = "id", required = true) Integer id,
                                                  @RequestBody VehicleLocationRequestDTO request){
        Vehicle vehicle = vehicleService.findOneById(id);
        if(vehicle == null){
            HttpStatusMessageDTO httpStatusMessageDTO = new HttpStatusMessageDTO("Vehicle does not exist!");
            return new ResponseEntity<HttpStatusMessageDTO>(httpStatusMessageDTO, HttpStatus.NOT_FOUND);
        }

        Location locationOld = vehicle.getLocation();
        if(locationOld!=null){
            locationOld.setAddress(request.getAddress());
            locationOld.setLatitude(request.getLatitude());
            locationOld.setLongitude(request.getLongitude());
            locationService.save(locationOld);
            vehicle.setLocation(locationOld);
        }else{
            Location location = new Location(request);
            locationService.save(location);
            vehicle.setLocation(location);
            vehicleService.save(vehicle);
        }

        HttpStatusMessageDTO response = new HttpStatusMessageDTO("Coordinates successfully updated");
        return new ResponseEntity<HttpStatusMessageDTO>(response, HttpStatus.NO_CONTENT);
    }
}
