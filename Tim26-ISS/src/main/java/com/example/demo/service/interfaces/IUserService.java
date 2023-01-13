package com.example.demo.service.interfaces;

import com.example.demo.model.Role;
import com.example.demo.model.User;

import java.util.List;

public interface IUserService {
    User save(User user);
    Role saveRole(Role role);
    void addRoleToUser(String email, String role);
    User getUser(String email);
    List<User> findAll();
    List<User> findAll(Integer pageNo, Integer pageSize);
    User findOneById(Integer id);

}
