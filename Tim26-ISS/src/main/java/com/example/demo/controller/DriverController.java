package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.dto.driver.*;
import com.example.demo.exceptions.*;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.DriverService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/driver")
public class DriverController {

    @Autowired
    private DriverService driverService;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private WorkingHourRepository workingHourRepository;
    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createDriver(@RequestBody DriverRequestDTO driverRequestDTO){
        User user = userRepository.findOneByEmail(driverRequestDTO.getEmail());
        if (user!=null){
            throw new UserAlreadyExistsException();
        }
        DriverResponseDTO saved = driverService.insert(new Driver(driverRequestDTO));
        return new ResponseEntity<>(saved,HttpStatus.OK);
    }

    // FUNKCIJA ZA ADMINA?
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    // TODO FUNKCIJA ZA ADMINA, ZASTO BI DRIVER IMAO PRISTUP INFORMACIJAMA OSTALIH VOZACA
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
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverResponseDTO> getDriverInfo(@PathVariable("id") Integer id, Principal userPrincipal){
        Optional<User> found = driverRepository.findById(id);
        if(found.isEmpty()){
            throw new UserDoesNotExistException();
        }
        if (!userPrincipal.getName().equals(found.get().getEmail()))
            throw new UserIdNotMatchingException();
        DriverResponseDTO response = new DriverResponseDTO((Driver) found.get());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverResponseDTO> updateDriver(@PathVariable("id") Integer id,@RequestBody DriverRequestDTO driver, Principal userPrincipal){
        Optional<User> found = driverRepository.findById(id);
        if(found.isEmpty()){
            throw new UserDoesNotExistException();
        }
        if (!userPrincipal.getName().equals(found.get().getEmail()))
            throw new UserIdNotMatchingException();
//        if (!userPrincipal.getName().equals(driver.getEmail()))
//            throw new ForbiddenDataUpdateException();
        DriverResponseDTO updated = driverService.update(driver,id);
        return new ResponseEntity<>(updated,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @PutMapping(value = "/{id}/activity",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateDriverActivity(@PathVariable("id") Integer id,@RequestParam(required = true) boolean status, Principal userPrincipal){
        Optional<User> found = driverRepository.findById(id);
        if(found.isEmpty()){
            throw new UserDoesNotExistException();
        }
        if (!userPrincipal.getName().equals(found.get().getEmail()))
            throw new UserIdNotMatchingException();
        User user = found.get();
        user.setActive(status);
        userRepository.save(user);
        userRepository.flush();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/documents",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    public ResponseEntity<?> getDocuments(@PathVariable("id") Integer id,Principal userPrincipal){
        Optional<User> found = driverRepository.findById(id);
        if (found.isEmpty())
            throw new UserDoesNotExistException();
        if (!found.get().getEmail().equals(userPrincipal.getName()))
            throw new UserIdNotMatchingException();
        Driver driver = (Driver) found.get();
        driver.setDocuments(driverService.getDocuments(driver.getId()));
        List<Document> docs = driver.getDocuments();
        List<DriverDocumentsResponseDTO> responseDTO = DriverDocumentsResponseDTO.returnDocs(docs);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/documents",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    public  ResponseEntity<DriverDocumentsResponseDTO> addDriverDocument(@PathVariable("id") Integer id,@RequestBody DriverDocumentsRequestDTO document,Principal userPrincipal){
        Optional<User> found = driverRepository.findById(id);
        if (found.isEmpty())
            throw new UserDoesNotExistException();
        if (!found.get().getEmail().equals(userPrincipal.getName()))
            throw new UserIdNotMatchingException();
        Driver driver = (Driver) found.get();
        driver.setDocuments(driverService.getDocuments(driver.getId()));
        List<Document> docs = driver.getDocuments();
        Document added = new Document(document);
        added.setDriver(driver);
        driverRepository.save(driver);
        documentRepository.save(added);
        return new ResponseEntity<>(new DriverDocumentsResponseDTO(added),HttpStatus.OK);
    }

    @DeleteMapping(value = "/document/{document-id}")
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    public ResponseEntity<?> deleteDriverDocument(@PathVariable("document-id") Integer documentId,Principal userPrincipal){
        Optional<Document> found = documentRepository.findById(documentId);
        if (found.isEmpty())
            throw new DocumentDoesNotExistException();
        Document document = found.get();
        if (!userPrincipal.getName().equals(document.getDriver().getEmail()))
            throw new ForbiddenDataUpdateException();
        documentRepository.deleteById(documentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{id}/working-hour",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    public ResponseEntity<DriverWorkingHoursDTO> getWorkingHours(@PathVariable("id") int id,
                                                                 @RequestParam(required = false) Integer page,
                                                                 @RequestParam(required = false) Integer size,
                                                                 @RequestParam(required = false) String from,
                                                                 @RequestParam(required = false) String to){
        DriverWorkingHoursDTO response = new DriverWorkingHoursDTO();
        response.setTotalCount(response.getResults().size());
        return new ResponseEntity<DriverWorkingHoursDTO>(response,HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/working-hour",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    public ResponseEntity<?> createWorkingHour(@PathVariable("id") Integer driverId,@RequestBody DriverStartWorkingHourDTO start,Principal userPrincipal) throws ParseException {
        Optional<User> found = driverRepository.findById(driverId);
        if (found.isEmpty())
            throw new UserDoesNotExistException();
        if (!found.get().getEmail().equals(userPrincipal.getName()))
            throw new UserIdNotMatchingException();
        Driver driver = (Driver) found.get();
        Hibernate.initialize(driver);
        WorkingHour workingHour = new WorkingHour(driver,start.getStart());
        workingHourRepository.save(workingHour);
        DriverWorkingHourResponse response = new DriverWorkingHourResponse(workingHour);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping(value = "/working-hour/{working-hour-id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    public ResponseEntity<?> getDetailedWorkingHour(@PathVariable("working-hour-id") Integer hourId, Principal userPrincipal){
        Optional<WorkingHour> check = workingHourRepository.findById(hourId);
        if (check.isEmpty())
            throw new WorkingHourDoesNotExistException();
        if (!userPrincipal.getName().equals(check.get().getDriver().getEmail()))
            throw new UserIdNotMatchingException();
        Hibernate.initialize(check.get());
        DriverWorkingHourResponse response = new DriverWorkingHourResponse(check.get());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping(value = "/working-hour/{working-hour-id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    public ResponseEntity<?> updateWorkingHour(@PathVariable("working-hour-id") Integer hourId,@RequestBody DriverEndWorkingHourDTO end,Principal userPrincipal) throws ParseException {
        Optional<WorkingHour> check = workingHourRepository.findById(hourId);
        if (check.isEmpty())
            throw new WorkingHourDoesNotExistException();
        if (!userPrincipal.getName().equals(check.get().getDriver().getEmail()))
            throw new UserIdNotMatchingException();
        Hibernate.initialize(check.get());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        check.get().setEndTime(formatter.parse(end.getEnd()));
        DriverWorkingHourResponse response = new DriverWorkingHourResponse(check.get());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/vehicle",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    public ResponseEntity<?> getDriversVehicle(@PathVariable("id") Integer driverId,Principal userPrincipal){
        Optional<User> found = driverRepository.findById(driverId);
        if (found.isEmpty())
            throw new UserDoesNotExistException();
        if (!found.get().getEmail().equals(userPrincipal.getName()))
            throw new UserIdNotMatchingException();
        Optional<Vehicle> foundVehicle = vehicleRepository.findOneByDriver((Driver) found.get());
        if (foundVehicle.isEmpty())
            throw new VehicleDoesNotExistException();
        DriverVehicleResponseDTO responseDTO = new DriverVehicleResponseDTO(foundVehicle.get());
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @PostMapping(value = "/{id}/vehicle",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> driverNewVehicle(@PathVariable("id") Integer driverId,@RequestBody DriverVehicleRequestDTO addVehicle, Principal userPrincipal){
        Optional<User> found = driverRepository.findById(driverId);
        if (found.isEmpty())
            throw new UserDoesNotExistException();
        if (!found.get().getEmail().equals(userPrincipal.getName()))
            throw new UserIdNotMatchingException();
        Driver driver = (Driver) found.get();
        if (driver.getVehicle()!=null)
            throw new DriverHasVehicleException();
        Vehicle added = new Vehicle(addVehicle);
        added.setDriver(driver);
        added.setVehicleType(vehicleTypeRepository.findOneByName(VehicleTypeEnum.valueOf(addVehicle.getVehicleType())));
        driver.setVehicle(added);
        driverRepository.save(driver);
        vehicleRepository.save(added);
        DriverVehicleResponseDTO response = new DriverVehicleResponseDTO(added);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/vehicle",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    public ResponseEntity<DriverVehicleResponseDTO> updateDriverVehicle(@PathVariable("id") Integer driverId,@RequestBody DriverVehicleRequestDTO update, Principal userPrincipal){
        Optional<User> found = driverRepository.findById(driverId);
        if (found.isEmpty())
            throw new UserDoesNotExistException();
        if (!found.get().getEmail().equals(userPrincipal.getName()))
            throw new UserIdNotMatchingException();
        Driver driver = (Driver) found.get();
        Optional<Vehicle> foundVehicle = vehicleRepository.findOneByDriver(driver);
        if (foundVehicle.isEmpty())
            throw new VehicleDoesNotExistException();
        // TODO set new data for this vehicle
        DriverVehicleResponseDTO responseDTO = driverService.updateVehicle(foundVehicle.get().getId(),update);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/ride",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MultipleRidesDTO> getAllRides(@PathVariable("id") Integer id,
                                                        @RequestParam(required = false) Integer page,
                                                        @RequestParam(required = false) Integer size,
                                                        @RequestParam(required = false) String sort,
                                                        @RequestParam(required = false) String from,
                                                        @RequestParam(required = false) String to){
        MultipleRidesDTO response = new MultipleRidesDTO();
        return new ResponseEntity<MultipleRidesDTO>(response,HttpStatus.OK);
    }

    @GetMapping(value="/report", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverReportDTO> getReports(@PathVariable("id") Integer driverId,Principal userPrincipal){
        Optional<User> found = driverRepository.findById(driverId);
        if (found.isEmpty())
            throw new UserDoesNotExistException();
        if (!found.get().getEmail().equals(userPrincipal.getName()))
            throw new UserIdNotMatchingException();
        DriverReportDTO response = driverService.getReports(driverId);
        return new ResponseEntity<DriverReportDTO>(response,HttpStatus.OK);
    }
}
