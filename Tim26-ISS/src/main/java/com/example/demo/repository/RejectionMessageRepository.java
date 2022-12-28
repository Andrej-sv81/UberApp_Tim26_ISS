package com.example.demo.repository;

import com.example.demo.model.RejectionMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RejectionMessageRepository extends JpaRepository<RejectionMessage, Integer> {
}
