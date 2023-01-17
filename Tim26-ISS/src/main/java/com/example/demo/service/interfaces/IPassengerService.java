package com.example.demo.service.interfaces;

import com.example.demo.model.Passenger;
import com.example.demo.model.Ride;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPassengerService {

    List<Ride> getRides(Integer id);

   // List<Ride> getRides(Integer id,Integer page, Integer size, String sort, String from, String to);
}
