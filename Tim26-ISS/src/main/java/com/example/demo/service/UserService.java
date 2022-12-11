package com.example.demo.service;

import com.example.demo.dto.UserRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.model.User;
import java.util.Collection;

public interface UserService {
    Collection<User> findAll();
    User findOne(int id);
    UserResponseDTO create(UserRequestDTO user) throws Exception;
    User activateAcc(int activationId);
    User update(int id) throws Exception;
}
