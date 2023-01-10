package com.example.demo.service;

import com.example.demo.dto.user.UserRequestDTO;
import com.example.demo.dto.user.UserResponseDTO;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
    public List<User> findAll() { return userRepository.findAll();}
    public Page<User> findAll(Pageable page){ return  userRepository.findAll(page);}
    public User findOne(int id) { return userRepository.findOneById(id);}
    public User findOneByEmail(String email){ return userRepository.findOneByEmail(email);}
    public User save(User user) {
        return userRepository.save(user);
    }
    public void remove(Integer id) {
        userRepository.deleteById(id);
    }

    public UserResponseDTO create(UserRequestDTO user) throws Exception {
        return null;
    }

    public User activateAcc(int activationId) {
        return null;
    }

    public User update(int id) throws Exception {
        return null;
    }
}
