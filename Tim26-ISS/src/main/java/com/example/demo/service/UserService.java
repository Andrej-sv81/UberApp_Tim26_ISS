package com.example.demo.service;

import com.example.demo.dto.user.UserRequestDTO;
import com.example.demo.dto.user.UserResponseDTO;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class UserService implements  IUserService { //UserDetailsService

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
//    @Bean
//    public BCryptPasswordEncoder passwordEncoderUser() {
//        return new BCryptPasswordEncoder();
//    }
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findOneByEmail(email);
//        if(user == null){
//            throw new UsernameNotFoundException("User not found in database");
//        }
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        user.getRoles().forEach(role -> {
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
//        });
//        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
//    }

    @Override
    public User save(User user) {
//        user.setPassword(passwordEncoderUser().encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public Role saveRole(Role role) {
        roleRepository.save(role);
        return role;
    }

    @Override
    public void addRoleToUser(String email, String rolename) {
        User user = userRepository.findOneByEmail(email);
        Role role = roleRepository.findByName(rolename);
        user.getRoles().add(role);
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
