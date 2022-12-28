package com.example.demo.service;

import com.example.demo.repository.RejectionMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RejectionMessageService {

    @Autowired
    private RejectionMessageRepository rejectionMessageRepository;
}
