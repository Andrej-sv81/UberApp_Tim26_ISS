package com.example.demo.service.interfaces;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.security.JwtTokenUtil;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {

    User save(User user);

    User saveEncode(User user);

    //Role saveRole(Role role);
    //void addRoleToUser(String email, String role);
    User getUser(String email);
    List<User> findAll();
    List<User> findAll(Integer pageNo, Integer pageSize);
    User findOneById(Integer id);
    User findUserByEmail(String email);
}
