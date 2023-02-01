package com.example.demo.service.interfaces;

import com.example.demo.model.UserActivation;

public interface IUserActivationService {
    void save(UserActivation activation);

    UserActivation findById(Integer activationId);

    void delete(UserActivation activation);
}
