package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService implements UserDetailsService, IUserService {

    @Autowired
    private UserRepository userRepository;
    public BCryptPasswordEncoder passwordEncoderUser() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> result = Optional.ofNullable(userRepository.findOneByEmail(email));

        if (result.isPresent()) {
            return org.springframework.security.core.userdetails.User.withUsername(email).password(result.get().getPassword()).roles(result.get().getRole()).build();
        }
        throw new UsernameNotFoundException("User not found with this email in database");
    }

    @Override
    public User save(User user) {
        userRepository.save(user);
        userRepository.flush();
        return user;
    }
    @Override
    public User saveEncode(User user) {
        user.setPassword(passwordEncoderUser().encode(user.getPassword()));
        userRepository.save(user);
        userRepository.flush();
        return user;
    }
    @Override
    public User getUser(String email) {
        return userRepository.findOneByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAll(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("user_id"));
        Page<User> pageResult = userRepository.findAll(pageable);
        if (pageResult.hasContent()) {
            return pageResult.getContent();
        } else {
            return new ArrayList<User>();
        }
    }
    @Override
    public User findOneById(Integer id) {
        Optional<User> found = userRepository.findById(id);
        if (found.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return found.get();
    }
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }


}
