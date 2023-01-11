package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.dto.driver.*;
import com.example.demo.model.Location;
import com.example.demo.model.WorkingHour;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/driver")
public class DriverController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverResponseDTO> createDriver(@RequestBody DriverRequestDTO driverRequestDTO){
        DriverResponseDTO response = new DriverResponseDTO();
        response.setId(1);
        return new ResponseEntity<DriverResponseDTO>(response, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MultipleDriversDTO> getDrivers(@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size){
        MultipleDriversDTO response = new MultipleDriversDTO();
        List<DriverResponseDTO> generetedDrivers = new ArrayList<DriverResponseDTO>();
        for (int i=0;i<10;i++){
            DriverResponseDTO dummy = new DriverResponseDTO();
            dummy.setId(i);
            generetedDrivers.add(dummy);
        }
        response.setResults(generetedDrivers);
        response.setTotalCount(generetedDrivers.size());
        return new ResponseEntity<MultipleDriversDTO>(response,HttpStatus.OK);
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverResponseDTO> getDriverInfo(@PathVariable("id") Integer id){
        DriverResponseDTO responseDTO = new DriverResponseDTO();
        responseDTO.setId(id);
        return new ResponseEntity<DriverResponseDTO>(responseDTO,HttpStatus.OK);
    }

    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverResponseDTO> updateDriver(@PathVariable("id") Integer id,@RequestBody DriverRequestDTO driver){
        DriverResponseDTO updated = new DriverResponseDTO();
        updated.setId(id);
        return new ResponseEntity<DriverResponseDTO>(updated,HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/documents",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DriverDocumentsResponseDTO>> getDocuments(@PathVariable("id") int id){
        List<DriverDocumentsResponseDTO> responseDTO = new ArrayList<DriverDocumentsResponseDTO>();
        DriverDocumentsResponseDTO doc = new DriverDocumentsResponseDTO();
        doc.setId(id);
        doc.setDriverId(id);
        responseDTO.add(doc);
        return new ResponseEntity<List<DriverDocumentsResponseDTO>>(responseDTO,HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/documents",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<DriverDocumentsResponseDTO> addDriverDocument(@PathVariable("id") int id,@RequestBody DriverDocumentsRequestDTO docs){
        DriverDocumentsResponseDTO responseDTO = new DriverDocumentsResponseDTO();
        responseDTO.setDriverId(id);
        responseDTO.setId(id);
        responseDTO.setName(docs.getName());
        return new ResponseEntity<DriverDocumentsResponseDTO>(responseDTO,HttpStatus.OK);
    }

    @DeleteMapping(value = "/document/{document-id}")
    public ResponseEntity<HttpStatus> deleteDriverDocument(@PathVariable("document-id") int documentId){
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{id}/working-hour",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverWorkingHoursDTO> getWorkingHours(@PathVariable("id") int id,
                                                                 @RequestParam(required = false) Integer page,
                                                                 @RequestParam(required = false) Integer size,
                                                                 @RequestParam(required = false) String from,
                                                                 @RequestParam(required = false) String to){
        DriverWorkingHoursDTO response = new DriverWorkingHoursDTO();
//        for (int i=0;i<10;i++){
//            WorkingHour wh = new WorkingHour(i, LocalDateTime.now(),LocalDateTime.now().plusHours(5));
//            response.getResults().add(wh);
//        }
        response.setTotalCount(response.getResults().size());
        return new ResponseEntity<DriverWorkingHoursDTO>(response,HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/working-hour",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkingHour> createWorkingHour(@PathVariable("id") int id,@RequestBody WorkingHour workingHour){
//        WorkingHour response = new WorkingHour(2,workingHour.getStart().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),workingHour.getEnd().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return new ResponseEntity<WorkingHour>(HttpStatus.OK);
    }

    @GetMapping(value = "/working-hour/{working-hour-id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkingHour> getDetailedWorkingHour(@PathVariable("working-hour-id") int id){
//        WorkingHour response = new WorkingHour(id,LocalDateTime.now(),LocalDateTime.now().plusHours(4));
        return new ResponseEntity<WorkingHour>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/vehicle",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverVehicleResponseDTO> getDriversVehicle(@PathVariable("id") int id){
        DriverVehicleResponseDTO response = new DriverVehicleResponseDTO();
        response.setCurrentLocation(new Location("Bulevar Jase tomica",15.11101001,23.09090932093));
        return new ResponseEntity<DriverVehicleResponseDTO>(response,HttpStatus.OK);
    }
    
    @PutMapping(value = "/working-hour/{working-hour-id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkingHour> updateWorkingHour(@PathVariable("working-hour-id") int id,@RequestBody WorkingHour updated){
        return new ResponseEntity<>(updated,HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/vehicle",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverVehicleResponseDTO> driverNewVehicle(@PathVariable("id") int id,@RequestBody DriverVehicleRequestDTO addVehicle){
        DriverVehicleResponseDTO response = new DriverVehicleResponseDTO();
        response.setCurrentLocation(new Location("Bulevar Jase tomica",15.11101001,23.09090932093));
        return new ResponseEntity<DriverVehicleResponseDTO>(response,HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/vehicle",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverVehicleResponseDTO> updateDriverVehicle(@PathVariable("id") int id,@RequestBody DriverVehicleRequestDTO update){
        DriverVehicleResponseDTO response = new DriverVehicleResponseDTO();
        response.setCurrentLocation(new Location("Bulevar Jase tomica",15.11101001,23.09090932093));
        return new ResponseEntity<DriverVehicleResponseDTO>(response,HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/ride",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MultipleRidesDTO> getAllRides(@PathVariable("id") int id,
                                                        @RequestParam(required = false) int page,
                                                        @RequestParam(required = false) int size,
                                                        @RequestParam(required = false) String sort,
                                                        @RequestParam(required = false) String from,
                                                        @RequestParam(required = false) String to){
        MultipleRidesDTO response = new MultipleRidesDTO();
        return new ResponseEntity<MultipleRidesDTO>(response,HttpStatus.OK);
    }
}
