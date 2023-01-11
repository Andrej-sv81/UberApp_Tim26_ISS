package com.example.demo.repository;

import com.example.demo.model.UserActivation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActivationRepository extends JpaRepository<UserActivation, Long> {
}
