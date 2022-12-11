package com.example.demo.repository;

import com.example.demo.dto.UserRequestDTO;
import com.example.demo.model.Ride;
import com.example.demo.model.User;

import java.util.Collection;

public interface UserRepository {

        Collection<User> findAll();
        User create(UserRequestDTO user);
        User findOne(int id);
        User update(User user);
        User activateAcc(int id);
        Collection<Ride> userGetRides(int id);
}
