package com.example.demo.service;

import com.example.demo.dto.ExplanationDTO;
import com.example.demo.dto.LocationDTO;
import com.example.demo.dto.RejectionDTO;
import com.example.demo.dto.RouteDTO;
import com.example.demo.dto.passenger.PassengerRequestDTO;
import com.example.demo.dto.passenger.PassengerRideOverDTO;
import com.example.demo.dto.ride.RideDTO;
import com.example.demo.dto.ride.RideRequestDTO;
import com.example.demo.dto.ride.RideResponseDTO;
import com.example.demo.exceptions.*;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.interfaces.IRideService;
import com.example.demo.util.cost.EstimatedCost;
import com.example.demo.websockethandler.RideHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.socket.WebSocketSession;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RideService implements IRideService {
    @Autowired
    private RideRepository rideRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private WorkingHourService workingHourService;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private RejectionMessageService rejectionMessageService;
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private RouteService routeService;
    @Autowired
    private DriverService driverService;



    public Ride findOneById(Integer id) {
        Optional<Ride> found = rideRepository.findOneById(id);
        if (found.isEmpty()) {
            throw new RideDoesNotExistException();
        }
        return found.get();
    }

    @Override
    public Driver getDriverOfRide(int rideId) {
        return rideRepository.getDriverOfRide(rideId);
    }

    @Override
    public void save(Ride ride) {
        rideRepository.save(ride);
        rideRepository.flush();
    }

    @Transactional
    @Override
    public List<Review> getReviews(Integer id) {
        return rideRepository.getReviews(id);
    }


    @Override
    public List<Ride> getRides(Integer id, Integer page, Integer size, String sort, String from, String to) {
        Pageable pageable = null;
        Page<Ride> pageResult = null;
        List<Ride> rides = null;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFrom = null;
        Date dateTo = null;
        try {
            if (!from.equals("")) {
                dateFrom = formatter.parse(from);
            }
            if (!to.equals("")) {
                dateTo = formatter.parse(to);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (page != null && size != null) {
            if (!sort.equals("")) {
                pageable = PageRequest.of(page, size, Sort.by(sort));
            } else {
                pageable = PageRequest.of(page, size, Sort.by("RIDE_ID"));
            }

            if (dateFrom != null) {
                if (dateTo != null) {
                    pageResult = rideRepository.getRides(id, dateFrom, dateTo, pageable);
                } else {
                    pageResult = rideRepository.getRides(id, dateFrom, pageable);
                }
            } else {
                pageResult = rideRepository.getRides(id, pageable);
            }
            rides = pageResult.getContent();
        } else {
            rides = rideRepository.getRides(id);
        }

        for (Ride r : rides) {
            System.out.println(r.getId() + "  " + r.getStartTime() + "  " + r.getTotalCost());
        }
        return rides;
    }

    @Override
    public List<Ride> getRidesPassenger(Integer id, Integer page, Integer size, String sort, String from, String to) {
        Pageable pageable = null;
        Page<Ride> pageResult = null;
        List<Ride> rides = null;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFrom = null;
        Date dateTo = null;
        try {
            if (!from.equals("")) {
                dateFrom = formatter.parse(from);
            }
            if (!to.equals("")) {
                dateTo = formatter.parse(to);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (page != null && size != null) {
            if (!sort.equals("")) {
                pageable = PageRequest.of(page, size, Sort.by(sort));
            } else {
                pageable = PageRequest.of(page, size, Sort.by("RIDE_ID"));
            }

            if (dateFrom != null) {
                if (dateTo != null) {
                    pageResult = rideRepository.getRidesPassenger(id, dateFrom, dateTo, pageable);
                } else {
                    pageResult = rideRepository.getRidesPassenger(id, dateFrom, pageable);
                }
            } else {
                pageResult = rideRepository.getRidesPassenger(id, pageable);
            }
            rides = pageResult.getContent();
        } else {
            rides = rideRepository.getRidesPassenger(id);
        }

        for (Ride r : rides) {
            System.out.println(r.getId() + "  " + r.getStartTime() + "  " + r.getTotalCost());
        }
        return rides;
    }

    @Override
    public List<Ride> findPendingRides(Integer id) {
        List<Ride> pendingRides =  rideRepository.findPendingRides(id);
        if(pendingRides.size() != 0){
            throw new HasPendingRideException();
        }else{
            return pendingRides;
        }
    }

    @Override
    public Ride findActiveRideForDriver(Integer driverId) {
        Ride ride = rideRepository.findActiveRideForDriver(driverId);
        if(ride == null){
            throw new ActiveRideDoesNotExistException();
        }
        return ride;
    }


    @Override
    public Ride findActiveRideForPassenger(Integer passengerId) {
        Ride ride = rideRepository.findActiveRideForPassenger(passengerId);
        if(ride == null){
            throw new ActiveRideDoesNotExistException();
        }
        return ride;
    }

    @Override
    public boolean validateRideRequestDTO(RideRequestDTO ride) {
        //TODO odradi validiranje zahteva za voznju
        return false;
    }

    @Override
    public boolean checkForPendingRide(String name) {
        //TODO odradi proveru da li postoji pending ride
        return false;
    }



    @Override
    public Object[] findSuitableDriver(Ride ride) {

        Object[] result = new Object[2];
        result[0] = null;
        result[1] = null;

        Driver bestDriver = new Driver();
        int minimumMinutes = Integer.MAX_VALUE;

        List<Driver> availableDrivers = this.driverRepository.getActiveDrivers();
        if (availableDrivers.isEmpty()) return result;

        List<Driver> suitableDrivers = new ArrayList<>();
        for (Driver driver : availableDrivers) {
            if (this.workingHourService.getTotalHoursWorkedInLastDay(driver.getId()).toHours() >= 8) continue;
            //if (this.findDriverScheduledRide(driver.getId()).isPresent()) continue;  TODO PITAJ JELENU ZA OVO!!!
            Optional<Vehicle> foundVehicle = vehicleRepository.findOneByDriver(driver);
            if (foundVehicle.isEmpty())
                continue;
            Vehicle vehicle = foundVehicle.get();
            if(!ride.getVehicleType().equals(vehicle.getVehicleType())) continue;
            if (ride.isBabyFlag())
                if (!vehicle.isBabyFlag()) continue;
            if (ride.isPetFlag())
                if (!vehicle.isPetFlag()) continue;
            if (ride.getPassengers().size() > vehicle.getNumberOfSeats()) continue;
            suitableDrivers.add(driver);
        }

        if (suitableDrivers.isEmpty()) return result;

        for (Driver driver : suitableDrivers) {
            Optional<Vehicle> foundVehicle = this.vehicleRepository.findOneByDriver(driver);
            if (foundVehicle.isEmpty())
                continue;
            Vehicle vehicle = foundVehicle.get();
            int minutes = 0;
            Ride currentRide = rideRepository.findActiveRideForDriver(driver.getId());
            if (currentRide != null) {
                minutes += (EstimatedCost.calculateDistance(vehicle.getLocation().getLatitude(),vehicle.getLocation().getLongitude(), currentRide.getRoutes().getDestination().getLatitude(),currentRide.getRoutes().getDestination().getLongitude()) / 80) * 60 * 2;
                minutes += (EstimatedCost.calculateDistance( currentRide.getRoutes().getDestination().getLatitude(),currentRide.getRoutes().getDestination().getLongitude(), ride.getRoutes().getStartLocation().getLatitude(), ride.getRoutes().getStartLocation().getLongitude()) / 80) * 60 * 2;
            } else {
                minutes += (EstimatedCost.calculateDistance(vehicle.getLocation().getLatitude(),vehicle.getLocation().getLongitude(), ride.getRoutes().getStartLocation().getLatitude(),ride.getRoutes().getStartLocation().getLongitude()) / 80) * 60 * 2;
            }
            if ((int) minutes < minimumMinutes) {
                bestDriver = driver;
                minimumMinutes = (int) minutes;
            }
        }

        result[0] = bestDriver;
        result[1] = (minimumMinutes + (EstimatedCost.calculateDistance(ride.getRoutes().getStartLocation().getLatitude(),ride.getRoutes().getStartLocation().getLongitude(),ride.getRoutes().getDestination().getLatitude(), ride.getRoutes().getDestination().getLongitude()) / 80) * 60 * 2);

        return result;
    }

    @Override
    public RideResponseDTO acceptRide(Integer id, Principal userPrincipal) {

        Optional<Ride> found = rideRepository.findOneById(id);
        if (found.isEmpty()) {
            throw new RideDoesNotExistException();
        }
        driverService.findByEmail(userPrincipal.getName()); // throws except if does not exist
        Ride ride = findOneById(id);
        if(ride.getRideState() != RideState.PENDING){
            throw new CannotAcceptRideException();
        }
        ride.setRideState(RideState.ACCEPTED);
        rideRepository.save(ride);
        RideResponseDTO initialDTO = new RideResponseDTO(ride);
        RideResponseDTO response = addFields(initialDTO);
        return response;
    }

    @Override
    public RideResponseDTO decline(Integer id, ExplanationDTO explanation, Principal userPrincipal) {
        Driver rideDriver = driverService.getDriverOfRide(id);
        if(!rideDriver.getEmail().equals(userPrincipal.getName())){
            throw new UserIdNotMatchingException();
        }
        Ride ride = findOneById(id);
        RejectionMessage rejectionMessage = new RejectionMessage(ride, explanation.getReason(),
                (User) rideDriver, Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        if(ride.getRideState() == RideState.PENDING || ride.getRideState() == RideState.STARTED){
            ride.setRideState(RideState.REJECTED);
            ride.setRejectionMessage(rejectionMessage);
        }else{
            throw new CannotCancelRideException();
        }
        ride.setRideState(RideState.REJECTED);
        this.rideRepository.save(ride);
        RideResponseDTO responseDTO = new RideResponseDTO(ride);
        RideResponseDTO response = addFields(responseDTO);
        return response;
    }

    @Override
    public RideResponseDTO endRide(Integer id, Principal userPrincipal) {
        Driver rideDriver = driverService.getDriverOfRide(id);
        if(!rideDriver.getEmail().equals(userPrincipal.getName())){
            throw new UserIdNotMatchingException();
        }
        Ride ride = findOneById(id);
        if (ride.getRideState() != RideState.ACTIVE){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Ride must be in state ACTIVE so you could finish it");
        }
        ride.setRideState(RideState.FINISHED);
        ride.setEndTime(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        save(ride);
        RideResponseDTO initalResponse = new RideResponseDTO(ride);
        RideResponseDTO response = addFields(initalResponse);
        return response;
    }

    @Override
    public RideResponseDTO startRide(Integer id, Principal userPrincipal) {
        Driver rideDriver = driverService.getDriverOfRide(id);
        if(!rideDriver.getEmail().equals(userPrincipal.getName())){
            throw new UserIdNotMatchingException();
        }
        Ride ride = findOneById(id);
        if(ride.getRideState() == RideState.ACCEPTED){
            ride.setRideState(RideState.STARTED);
            ride.setStartTime(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        }else{
            throw new CannotStartRideException();
        }
        save(ride);
        RideResponseDTO initalResponse = new RideResponseDTO(ride);
        RideResponseDTO response = addFields(initalResponse);
        return response;
    }

    @Override
    public RideResponseDTO cancelRidePassenger(Integer id, Principal userPrincipal) {
        Passenger passenger = passengerService.findPassengerByEmail(userPrincipal.getName());
        List<Passenger> passengerList = passengerService.getPassengersOfRide(id);
        boolean contains = false;
        for(Passenger p: passengerList){
            if(p.getId() == passenger.getId()){
                contains = true;
            }
        }
        if(!contains){
            throw new UserIdNotMatchingException();
        }
        Ride ride = findOneById(id);
        if(ride.getRideState() == RideState.PENDING || ride.getRideState() == RideState.STARTED){
            ride.setRideState(RideState.CANCELED);
        }else{
            throw new CannotCancelRideException();
        }
        save(ride);
        RideResponseDTO canceled = new RideResponseDTO(ride);
        RideResponseDTO response = addFields(canceled);
        return response;
    }



    // GLAVNA FUNKCIJA ZA KREIRANJE VOZNJE
    @Override
    public RideResponseDTO createRide(RideRequestDTO rideRequestDTO, String username) {
        //TODO SCHEDULED TIME?
        Ride ride = new Ride();
        List<RouteDTO> routeDTOS = rideRequestDTO.getLocations();
        Route route = this.routeRepository.save(new Route(routeDTOS.get(0)));
        ride.setRoute(route);
        ride.setBabyFlag(rideRequestDTO.isBabyTransport());
        ride.setPetFlag(rideRequestDTO.isPetTransport());
        ride.setVehicleType(vehicleTypeRepository.findOneByName(VehicleTypeEnum.getType(rideRequestDTO.getVehicleType())));
        ride.setRideState(RideState.PENDING);
//        for (PassengerRequestDTO passengerRequestDTO : rideRequestDTO.getPassengers()) {
//            Passenger pass = this.passengerRepository.findByEmail(userDTO.getEmail());
//            if (pass != null)
//                passengers.add(pass);
//        }
        //ride.setPassengers(rideRequestDTO.getPassengers());
        //Object[] result = findSuitableDriver(ride);

        //TODO AKO JE SCHEDULED ONDA IDE NEKI IF

        //System.out.println("PRONAJDENI VOZAC JE: " + result[0].toString());
        Optional<User> driverFound = driverRepository.findById(2); // FIKSNI VOZAC DEJAN
        Driver driver = (Driver) driverFound.get();
        List<Ride> rides = this.driverRepository.getRides(driver.getId());
        rides.add(ride);
        driver.setRides(rides);
        System.out.println(ride.getDriver().getId());
        return new RideResponseDTO();
    }

    // PRONALAZI POLJA ZA KOJA JE NEOPHODNA KOMUNIKACIJA SA OSTALIM SERVISIMA/REPOZITORIJUMIMA
    @Override
    public RideResponseDTO addFields(RideResponseDTO rideResponseDTO){
        RejectionDTO rejection = new RejectionDTO(rejectionMessageService.getMessageFromRide(rideResponseDTO.getId()));
        List<PassengerRideOverDTO> passengers = new ArrayList<PassengerRideOverDTO>();
        List<Passenger> ridePassengers = passengerService.getPassengersOfRide(rideResponseDTO.getId());
        for(Passenger p: ridePassengers){
            passengers.add(new PassengerRideOverDTO(p.getId(), p.getEmail()));
        }
        List<RouteDTO> routes = new ArrayList<RouteDTO>();
        List<Route> rideRoutes = routeService.getRoutesFromRide(rideResponseDTO.getId());
        for(Route r: rideRoutes){
            routes.add(new RouteDTO(new LocationDTO(r.getStartLocation()), new LocationDTO(r.getDestination())));
        }
        rideResponseDTO.setPassengers(passengers);
        rideResponseDTO.setLocations(routes);
        rideResponseDTO.setRejection(rejection);
        return rideResponseDTO;
    }
}
