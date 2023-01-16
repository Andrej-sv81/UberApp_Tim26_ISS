package com.example.demo.service;

import com.example.demo.model.User;
//import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService implements UserDetailsService, IUserService { //UserDetailsService

    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private RoleRepository roleRepository;
    @Autowired

    public BCryptPasswordEncoder passwordEncoderUser() {
        return new BCryptPasswordEncoder();
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> result = Optional.ofNullable(userRepository.findOneByEmail(email));

        if (result.isPresent()){
            return org.springframework.security.core.userdetails.User.withUsername(email).password(result.get().getPassword()).roles(result.get().getRole().toString()).build();
        }
        throw  new UsernameNotFoundException("User not found with this email in database");
    }


    @Override
    public User save(User user) {
//        user.setPassword(passwordEncoderUser().encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

//    @Override
//    public Role saveRole(Role role) {
//        roleRepository.save(role);
//        return role;
//    }

//    @Override
//    public void addRoleToUser(String email, String rolename) {
////        User user = userRepository.findOneByEmail(email);
////        Role role = roleRepository.findByName(rolename);
////        user.getRoles().add(role);
//    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
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
        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by("user_id"));
        Page<User> pageResult = userRepository.findAll(pageable);
        if(pageResult.hasContent()){
            return pageResult.getContent();
        }else {
            return new ArrayList<User>();
        }
    }
    @Override
    public User findOneById(Integer id) {
        return userRepository.findOneById(id);
    }


}
