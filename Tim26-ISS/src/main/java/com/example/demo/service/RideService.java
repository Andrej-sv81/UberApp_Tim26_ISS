package com.example.demo.service;

import com.example.demo.dto.ExplanationDTO;
import com.example.demo.dto.LocationDTO;
import com.example.demo.dto.RejectionDTO;
import com.example.demo.dto.RouteDTO;
import com.example.demo.dto.driver.DriverRideOverDTO;
import com.example.demo.dto.passenger.PassengerRideOverDTO;
import com.example.demo.dto.ride.RideDTO;
import com.example.demo.dto.ride.RidePassengerDTO;
import com.example.demo.dto.ride.RideRequestDTO;
import com.example.demo.dto.ride.RideResponseDTO;
import com.example.demo.exceptions.*;
import com.example.demo.model.*;
import com.example.demo.repository.DriverRepository;
import com.example.demo.repository.RideRepository;
import com.example.demo.service.interfaces.IRideService;
import com.example.demo.util.cost.EstimatedCost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.server.ResponseStatusException;

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
    private VehicleTypeService vehicleTypeService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private RideService rideService;
    @Autowired
    RejectionMessageService rejectionMessageService;
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
    public RideResponseDTO rideDetails(Integer id) {
        Optional<Ride> found = rideRepository.findOneById(id);
        if (found.isEmpty()) {
            throw new RideDoesNotExistException();
        }
        RideResponseDTO inital = new RideResponseDTO(found.get());
        RideResponseDTO response = addFields(inital);
        return response;
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
    public RideResponseDTO findActiveRideForDriver(Integer driverId) {
        Ride ride = rideRepository.findActiveRideForDriver(driverId);
        if(ride == null){
            throw new ActiveRideDoesNotExistException();
        }
        RideResponseDTO initial = new RideResponseDTO(ride);
        RideResponseDTO response = addFields(initial);
        return response;
    }

    @Override
    public RideResponseDTO findActiveRideForPassenger(Integer passengerId) {
        Ride ride = rideRepository.findActiveRideForPassenger(passengerId);
        if(ride == null){
            throw new ActiveRideDoesNotExistException();
        }
        RideResponseDTO inital = new RideResponseDTO(ride);
        RideResponseDTO response = addFields(inital);
        return response;
    }

    @Override
    public RideResponseDTO createRide(RideRequestDTO request, Principal userPrincipal) {

        Passenger passenger = passengerService.findPassengerByEmail(userPrincipal.getName());
        List<Ride> pendingRides = rideService.findPendingRides(passenger.getId()); //checks for pending rides

        VehicleType vehicleType = vehicleTypeService.findOneByName(
                VehicleTypeEnum.getType(request.getVehicleType()));
        double[] timeCostAndDistance = calculateTimeAndCost(request.getLocations().get(0), vehicleType);
        int estimated_time = (int)timeCostAndDistance[0];
        int total_cost = (int)timeCostAndDistance[1];
        double distance = timeCostAndDistance[2];

        List<Passenger> passengerList = new ArrayList<Passenger>();
        for(PassengerRideOverDTO p: request.getPassengers()){
            Passenger newP = passengerService.findPassengerByEmail(p.getEmail());
            passengerList.add(newP);
        }

        List<Route> routeList = new ArrayList<Route>();
        Location start = new Location(request.getLocations().get(0).getDeparture());
        Location end = new Location(request.getLocations().get(0).getDestination());
        Route route = new Route(start, end, distance);
        routeList.add(route);

        List<Review> reviews = new ArrayList<Review>();

        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date scheduledTime;
        try {
            if(!request.getScheduledTime().equals("")) {
                scheduledTime = formatter.parse(request.getScheduledTime());
            }else {
                scheduledTime = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault())
                        .toInstant());;
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Ride newRide = new Ride(null, null, total_cost, null,
                passengerList, routeList, estimated_time, reviews,
                RideState.PENDING, null, false,
                request.isBabyTransport(), request.isPetTransport(), vehicleType, scheduledTime);
        Optional<User> proba = driverRepository.findById(2);        // TODO ispravi vozaca fiksirano je na dejan@gmail.com
        newRide.setDriver((Driver) proba.get());
        save(newRide);

        for(Passenger p: passengerList){ // Petlja za bidirekciono cuvanje, jer ne mozemo cascadeAll zbog Dethached entity WTF???????
            List<Ride> rides = p.getRides();
            rides.add(newRide);
            p.setRides(rides);
            passengerService.insert(p);
        }

        RideResponseDTO response = new RideResponseDTO(newRide, request.getPassengers(), request.getLocations());
        DriverRideOverDTO probaResponse = new DriverRideOverDTO(proba.get().getId(),proba.get().getEmail());
        response.setDriver(probaResponse);
        return response;
    }

    @Override
    public boolean validateRideRequestDTO(RideRequestDTO ride) {
        return false;
    }

    @Override
    public boolean checkForPendingRide(String name) {
        return false;
    }

    @Override
    public Object[] findSuitableDriver(Ride ride) {
        return new Object[0];       // TODO kada se bude implementiralo biranje najblizeg vozaca
    }

    @Override
    public RideResponseDTO acceptRide(Integer id, Principal principal) {
        Optional<Ride> found = rideRepository.findOneById(id);
        if (found.isEmpty()) {
            throw new RideDoesNotExistException();
        }
        driverService.findByEmail(principal.getName()); // throws except if does not exist
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
    public RideResponseDTO decline(Integer id, ExplanationDTO explanationDTO, Principal userPrincipal) {
        Driver rideDriver = driverService.getDriverOfRide(id);
        if(!rideDriver.getEmail().equals(userPrincipal.getName())){
            throw new UserIdNotMatchingException();
        }
        Ride ride = findOneById(id);
        RejectionMessage rejectionMessage = new RejectionMessage(ride, explanationDTO.getReason(),
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
    public RideResponseDTO endRide(Integer id, Principal principal) {
        Driver rideDriver = driverService.getDriverOfRide(id);
        if(!rideDriver.getEmail().equals(principal.getName())){
            throw new UserIdNotMatchingException();
        }
        Ride ride = findOneById(id);
        if (ride.getRideState() != RideState.STARTED){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Ride must be in started so you could finish it");
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
    public RideResponseDTO cancelRidePassenger(Integer id, Principal principal) {
        Passenger passenger = passengerService.findPassengerByEmail(principal.getName());
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


    public double[] calculateTimeAndCost(RouteDTO route, VehicleType type){
        LocationDTO start = route.getDeparture();
        LocationDTO finish = route.getDestination();
        double distance = EstimatedCost.calculateDistance(start.getLatitude(), start.getLongitude(),
                finish.getLatitude(), finish.getLongitude());

        double price = type.getPrice() + distance * 120;
        double time = distance/80 * 60;

        return new double[]{time, price, distance};
    }

    @CrossOrigin(origins = "http://localjost:4200")
    public void notifyPassengers(RideDTO ride){
        for (RidePassengerDTO p : ride.getPassengers()){
            simpMessagingTemplate.convertAndSend("/topic/passenger/ride/"+p.getId(), ride);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    public void vehicleLocationUpdate(Integer rideId, LocationDTO update) {
        Ride r = rideRepository.findById(rideId).get();
        for(Passenger p: r.getPassengers()){
            simpMessagingTemplate.convertAndSend("/topic/vehicleLocation/ride/user/"+p.getId(), update);
        }
        simpMessagingTemplate.convertAndSend("/topic/vehicleLocation/ride/user/"+r.getDriver().getId(), update);

    }

    // PRONALAZI POLJA ZA KOJA JE NEOPHODNA KOMUNIKACIJA SA OSTALIM SERVISIMA/REPOZITORIJUMIMA
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
