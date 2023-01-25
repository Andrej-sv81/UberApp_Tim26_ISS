package com.example.demo.service;

import com.example.demo.exceptions.ActiveRideDoesNotExistException;
import com.example.demo.exceptions.HasPendingRideException;
import com.example.demo.exceptions.RideDoesNotExistException;
import com.example.demo.model.Driver;
import com.example.demo.model.Review;
import com.example.demo.model.Ride;
import com.example.demo.repository.RideRepository;
import com.example.demo.service.interfaces.IRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RideService implements IRideService {

    @Autowired
    private RideRepository rideRepository;

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

}
