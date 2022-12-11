package com.example.demo.service;

import com.example.demo.dto.UserRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.model.User;
import java.util.Collection;

//TODO Corect user Service and Repository for use with a database;
// both are copies of passenger serv and repo as placeholders

public class UserServiceImpl implements UserService{
    @Override
    public Collection<User> findAll() {
        return null;
    }

    @Override
    public User findOne(int id) {
        return null;
    }

    @Override
    public UserResponseDTO create(UserRequestDTO user) throws Exception {
        return null;
    }

    @Override
    public User activateAcc(int activationId) {
        return null;
    }

    @Override
    public User update(int id) throws Exception {
        return null;
    }
}
