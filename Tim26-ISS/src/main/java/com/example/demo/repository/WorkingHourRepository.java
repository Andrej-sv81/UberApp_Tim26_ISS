package com.example.demo.repository;

import com.example.demo.model.WorkingHour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkingHourRepository extends JpaRepository<WorkingHour, Long> {
}
