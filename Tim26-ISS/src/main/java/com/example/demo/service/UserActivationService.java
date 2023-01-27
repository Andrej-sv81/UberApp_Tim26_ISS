package com.example.demo.service;

import com.example.demo.exceptions.ActivationDoesNotExistException;
import com.example.demo.exceptions.UserDoesNotExistException;
import com.example.demo.model.User;
import com.example.demo.model.UserActivation;
import com.example.demo.repository.UserActivationRepository;
import com.example.demo.service.interfaces.IUserActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserActivationService implements IUserActivationService {

    @Autowired
    private UserActivationRepository userActivationRepository;

    @Override
    public void save(UserActivation activation) {
        userActivationRepository.save(activation);
        userActivationRepository.flush();
    }

    @Override
    public UserActivation findById(Integer activationId) {
        Optional<UserActivation> found = userActivationRepository.findById(activationId);
        if (found.isEmpty()) {
            throw new ActivationDoesNotExistException();
        }
        return found.get();
    }

    @Override
    public void delete(UserActivation activation) {
        userActivationRepository.delete(activation);
    }
}
