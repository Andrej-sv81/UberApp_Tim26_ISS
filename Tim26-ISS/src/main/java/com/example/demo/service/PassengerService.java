package com.example.demo.service;

import com.example.demo.dto.passenger.PassengerUpdateRequestDTO;
import com.example.demo.exceptions.UserDoesNotExistException;
import com.example.demo.security.JwtTokenUtil;
import com.example.demo.dto.passenger.PassengerRequestDTO;
import com.example.demo.dto.passenger.PassengerResponseDTO;
import com.example.demo.model.Passenger;
import com.example.demo.model.User;
import com.example.demo.model.Ride;
import com.example.demo.repository.PassengerRepository;
import com.example.demo.service.interfaces.IPassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PassengerService implements IPassengerService {


    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public List<User> getAll() {
        return passengerRepository.findAll();
    }

    @Override
    public Passenger findPassenger(Integer passengerId) {
        Optional<User> found = passengerRepository.findById(passengerId);
        if (found.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Passenger not found in database.");
        }
        return (Passenger) found.get();
    }

    @Override
    public PassengerResponseDTO insert(Passenger passenger) {
        Passenger saved;
        Passenger check = findPassengerByEmailNoException(passenger.getEmail()); // checking if we already have passenger with that email
        if (check != null) {
            return null;
        }
        saved = passengerRepository.save(passenger);
        passengerRepository.flush();
        return new PassengerResponseDTO(saved);
    }

    @Override
    public PassengerResponseDTO update(PassengerUpdateRequestDTO edited, Integer id) {
        try {
            Passenger passenger = findPassenger(id); //thiss will throw exception if passenger not found
            passenger.updatePassenger(edited);
            passengerRepository.save(passenger);
            passengerRepository.flush();
            return new PassengerResponseDTO(passenger);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Passenger delete(Integer passengerId) {
        Optional<User> found = passengerRepository.findById(passengerId);
        if (found.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Passenger not found in database.");
        }
        passengerRepository.delete(found.get());
        passengerRepository.flush();
        return (Passenger) found.get();
    }

    @Override
    public void deleteAll() {
        passengerRepository.deleteAll();
        passengerRepository.flush();
    }

    @Override
    public List<Ride> getRides(Integer id) {
        return passengerRepository.getRides(id);
    }

    @Override
    public Passenger findPassengerByEmail(String mail) {
        Optional<User> found = passengerRepository.findByEmail(mail);
        
        if(found.isEmpty()){
            throw new UserDoesNotExistException();
        }
        return (Passenger) found.get();
    }

    @Override
    public Passenger findPassengerByEmailNoException(String mail) {
        Optional<User> found = passengerRepository.findByEmail(mail);
        if(found.isEmpty()){
            return null;
        }
        return (Passenger) found.get();
    }


//    @Override
//    public List<Ride> getRides(Integer id,Integer page, Integer size, String sort, String from, String to) {
//        Pageable pageable;
//        Page<Ride> pageResult;
//        if (page != null && size != null) {
//            if (sort != null) {
//                pageable = PageRequest.of(page, size, Sort.by(sort));
//            } else {
//                pageable = PageRequest.of(page, size, Sort.by("ride_id"));
//            }
//           // TODO NATIVE QUERRY WITH PAGEING pageResult = passengerRepository.findAll(pageable);
//            pageResult = null;
//            if (pageResult.hasContent()) {
//                return pageResult.getContent();
//            }
//        }
//
//        return passengerRepository.getRides(id);
//    }
//
//    private List<Ride> sortRides(String from, String to, List<Ride> rides) throws ParseException {
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
//        List<Ride> ridesSorted = new ArrayList<Ride>();
//        for(Ride r: rides){
//            if(r.getStartTime().after(formatter.parse(from)) && r.getEndTime().before(formatter.parse(to))){
//                ridesSorted.add(r);
//            }
//        }
//        return ridesSorted;
//    }

    @Override
    public List<Passenger> getPassengersOfRide(Integer id) {
        return passengerRepository.getPassengerOfRide(id);

    }

    @Override
    public List<Passenger> getPassengersOfFavoriteRide(Integer id) {
        return passengerRepository.getPassengerOfFavoriteRide(id);
    }
}