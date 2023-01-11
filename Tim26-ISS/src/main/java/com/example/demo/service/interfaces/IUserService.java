package com.example.demo.service.interfaces;

import com.example.demo.model.Role;
import com.example.demo.model.User;

public interface IUserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String email, String role);
    User getUser(String email);
}
