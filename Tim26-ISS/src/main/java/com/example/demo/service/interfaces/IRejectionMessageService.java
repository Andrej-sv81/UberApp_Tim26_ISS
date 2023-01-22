package com.example.demo.service.interfaces;

import com.example.demo.model.RejectionMessage;

public interface IRejectionMessageService {
    RejectionMessage getMessageFromRide(Integer id);
}
