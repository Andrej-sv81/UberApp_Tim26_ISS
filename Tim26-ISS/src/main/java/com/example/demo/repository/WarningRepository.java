package com.example.demo.repository;

import com.example.demo.model.Warning;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarningRepository extends JpaRepository<Warning, Integer> {
}
