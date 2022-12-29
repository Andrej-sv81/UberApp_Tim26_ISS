package com.example.demo.service;

import com.example.demo.repository.WorkingHourRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class WorkingHourService {

    @Autowired
    private WorkingHourRepository workingHourRepository;
}
