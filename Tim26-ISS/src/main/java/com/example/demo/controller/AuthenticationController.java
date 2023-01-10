package com.example.demo.controller;



import com.example.demo.dto.token.JwtAuthenticationRequestDTO;
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


//Kontroler zaduzen za autentifikaciju korisnika
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

//TODO    @Autowired
//    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    // Prvi endpoint koji pogadja korisnik kada se loguje.
    // Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
    @PostMapping("/login")
    public ResponseEntity<UserTokenStateDTO> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequestDTO authenticationRequest, HttpServletResponse response) {
        // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
        // AuthenticationException
//TODO        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
        // kontekst
       //TODO SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
       //TODO User user = (User) authentication.getPrincipal();
       //TODO String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
       //TODO return ResponseEntity.ok(new UserTokenStateDTO(jwt, expiresIn));
        return null;
    }

    // Endpoint za registraciju novog korisnika
    @PostMapping("/signup")
    public ResponseEntity<User> addUser(@RequestBody UserRequestDTO userRequest, UriComponentsBuilder ucBuilder) {
        //TODO User existUser = this.userService.findByUsername(userRequest.getUsername());

//TODO        if (existUser != null) {
//            throw new ResourceConflictException(userRequest.getId(), "Username already exists");
//        }

        //TODO User user = this.userService.save(userRequest);

//TODO        return new ResponseEntity<>(user, HttpStatus.CREATED);
        return null;
    }
}