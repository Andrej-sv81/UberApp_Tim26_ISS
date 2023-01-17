package com.example.demo.service;

import com.example.demo.model.Ride;
import com.example.demo.model.User;
import com.example.demo.repository.PassengerRepository;
import com.example.demo.service.interfaces.IPassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class PassengerService implements IPassengerService {

    @Autowired
    private PassengerRepository passengerRepository;


    @Override
    public List<Ride> getRides(Integer id) {
        return passengerRepository.getRides(id);
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
}