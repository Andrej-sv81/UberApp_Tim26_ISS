package com.example.demo.controller;


import com.example.demo.dto.token.UserTokenStateDTO;
import com.example.demo.dto.user.UserRequestDTO;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.util.TokenUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
//TODO OVA KLASA TREBA BITI IZBRISANA I ZAMJENJENA
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController{
    @Autowired
    private TokenUtils tokenUtils;

   @Autowired
   private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<User> addUser(@RequestBody UserRequestDTO userRequest, UriComponentsBuilder ucBuilder) {
        User existUser = this.userService.findOneByEmail(userRequest.getEmail());

//TODO        if (existUser != null) {
//            throw new ResourceConflictException(userRequest.getId(), "Username already exists");
//        }
//        User user = this.userService.save(userRequest);
//        return new ResponseEntity<>(user, HttpStatus.CREATED);

        return null;
    }
}