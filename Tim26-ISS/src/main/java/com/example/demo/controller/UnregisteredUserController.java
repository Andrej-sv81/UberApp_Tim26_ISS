package com.example.demo.controller;

import com.example.demo.dto.ride.RidePathDTO;
import com.example.demo.dto.user.UnregisteredRequestDTO;
import com.example.demo.dto.user.UnregisteredResponseDTO;
import com.example.demo.model.VehicleType;
import com.example.demo.model.VehicleTypeEnum;
import com.example.demo.service.VehicleService;
import com.example.demo.service.VehicleTypeService;
import com.example.demo.util.cost.EstimatedCost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/unregisteredUser")
public class UnregisteredUserController {

    @Autowired
    VehicleTypeService vehicleTypeService;

    //TODO API iz primjera simulacije vozila, moze da vrati informacije o duzini putanje
    @PostMapping( value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UnregisteredResponseDTO>  rideAssumption(
            @Valid @RequestBody UnregisteredRequestDTO request){

        RidePathDTO path = request.getLocations().get(0);
        double lat1 = path.getDeparture().getLatitude();
        double lon1 = path.getDeparture().getLongitude();
        double lat2 = path.getDestination().getLatitude();
        double lon2 = path.getDestination().getLongitude();
        double resultDistance = EstimatedCost.calculateDistance(lat1, lon1, lat2, lon2);

        String typeString = request.getVehicleType();
        boolean babyFlag = request.isBabyTransport();
        boolean petFlag = request.isPetTransport();
        VehicleType type = vehicleTypeService.findOneByName(VehicleTypeEnum.getType(typeString));

        double price = type.getPrice() + resultDistance * 120;
        double minutes = resultDistance/80 * 60;
        UnregisteredResponseDTO responseDTO = new UnregisteredResponseDTO((int)minutes, (int)price);
        return  new ResponseEntity<UnregisteredResponseDTO>(responseDTO, HttpStatus.OK);
    }

}
