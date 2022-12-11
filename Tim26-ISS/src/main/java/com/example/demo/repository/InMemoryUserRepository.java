package com.example.demo.repository;

import com.example.demo.dto.UserRequestDTO;
import com.example.demo.model.Ride;
import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class InMemoryUserRepository implements UserRepository{
    @Override
    public Collection<User> findAll() {
        return null;
    }

    @Override
    public User create(UserRequestDTO user) {
        return null;
    }

    @Override
    public User findOne(int id) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User activateAcc(int id) {
        return null;
    }

    @Override
    public Collection<Ride> userGetRides(int id) {
        return null;
    }
}
