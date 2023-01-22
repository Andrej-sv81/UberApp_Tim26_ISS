package com.example.demo.service;

import com.example.demo.model.RejectionMessage;
import com.example.demo.repository.RejectionMessageRepository;
import com.example.demo.service.interfaces.IRejectionMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RejectionMessageService implements IRejectionMessageService {

    @Autowired
    private RejectionMessageRepository rejectionMessageRepository;
    @Override
    public RejectionMessage getMessageFromRide(Integer id) {
        return rejectionMessageRepository.getMessageFromRide(id);
    }
}
