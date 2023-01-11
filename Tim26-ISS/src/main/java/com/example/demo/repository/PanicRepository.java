package com.example.demo.repository;

import com.example.demo.model.Panic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PanicRepository extends JpaRepository<Panic, Long> {
}
