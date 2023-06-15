package com.example.demo.service;

import com.example.demo.dto.LocationDTO;
import com.example.demo.exceptions.VehicleDoesNotExistException;
import com.example.demo.model.*;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.VehicleRepository;
import com.example.demo.service.interfaces.IVehicleService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class VehicleService implements IVehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    RideService rideService;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public Vehicle findOneById(Integer id) {
        Optional<Vehicle> found = vehicleRepository.findOneById(id);
        if (found.isEmpty()) {
            throw new VehicleDoesNotExistException();
        }
        return found.get();
    }

    @Override
    public void save(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
        vehicleRepository.flush();
    }
    @Override
    @Transactional
    public List<Review> getReviews(Integer id) {
        return vehicleRepository.getReviews(id);
    }

    @Override
    public void simulateVehicle(Integer id) {
        Ride ride = rideService.findOneById(id);
        Vehicle vehicle = this.vehicleRepository.findOneByDriver(ride.getDriver()).get();
        vehicle.setLocation(locationRepository.findById(vehicle.getLocation().getId()).get());
        String start = "", end = "";
        if(ride.getRideState() == RideState.ACCEPTED){
            start = vehicle.getLocation().getLongitude() + "," + vehicle.getLocation().getLatitude();
            Route route = ride.getRoutes().get(0);
            end = route.getStartLocation().getLongitude() + "," + route.getStartLocation().getLatitude();

        }else if(ride.getRideState() == RideState.STARTED){
            Route route = ride.getRoutes().get(0);
            start = route.getStartLocation().getLongitude() + "," + route.getStartLocation().getLatitude();
            end = route.getDestination().getLongitude() + "," + route.getDestination().getLatitude();
            vehicle.setLocation(this.locationRepository.findById(route.getStartLocation().getId()).get());

        }else return;

        List<LocationDTO> route = this.getRouteFromOpenRoute(start, end);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int steps = 0;
            @Override
            public void run() {
                if(ride.getRideState()== RideState.FINISHED || ride.getRideState() == RideState.CANCELED){
                    Route routeRide = ride.getRoutes().get(0);
                    Location location = routeRide.getDestination();
                    location = locationRepository.save(location);
                    vehicle.setLocation(location);
                    vehicleRepository.save(vehicle);

                    steps = route.size();
                }
                if(steps < route.size()){
                    Location location = vehicle.getLocation();
                    saveLocationForCurrentRide(location, route, steps, vehicle);
                    //RideSimulationHandler.notifyUsersAboutVehicleLocations(sessions, location);
                    LocationDTO locationDTO = new LocationDTO(vehicle.getId(),location);
                    simpMessagingTemplate.convertAndSend("/map-updates/update-vehicle-position", locationDTO);
                    steps++;
                }else timer.cancel();
            }
        }, 1000, 2000);
    }

    @Override
    public List<LocationDTO> getRouteFromOpenRoute(String start, String end) {
        String base = "https://api.openrouteservice.org/v2/directions/driving-car";
        String key = "5b3ce3597851110001cf624826198d29de4c4c5d96dd61fd55b585bd";
        String url = base + "?api_key=" + key + "&start="+start+"&end="+end;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.GET, request, String.class);
        String responseString = response.getBody();
        JSONObject json = new JSONObject(responseString);
        JSONArray features = json.getJSONArray("features");
        JSONObject ff = features.getJSONObject(0);
        JSONObject geometry = ff.getJSONObject("geometry");
        JSONArray coords = geometry.getJSONArray("coordinates");
        List<LocationDTO> route = new ArrayList<>();
        for (int i = 0; i < coords.length(); i++) {
            JSONArray coord = coords.getJSONArray(i);
            double longitude = coord.getDouble(0);
            double latitude = coord.getDouble(1);
            LocationDTO location = new LocationDTO();
            location.setLongitude(longitude);
            location.setLatitude(latitude);
            route.add(location);
        }
        return route;
    }

    private void saveLocationForCurrentRide(Location location, List<LocationDTO> route, int totalPoints, Vehicle vehicle) {
        location.setLongitude(route.get(totalPoints).getLongitude());
        location.setLatitude(route.get(totalPoints).getLatitude());
        location = locationRepository.save(location);
        vehicle.setLocation(location);
        vehicleRepository.save(vehicle);
    }


}
