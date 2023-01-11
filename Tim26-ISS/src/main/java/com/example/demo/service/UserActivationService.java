package com.example.demo.service;

import com.example.demo.repository.UserActivationRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserActivationService {

    @Autowired
    private UserActivationRepository userActivationRepository;
}
