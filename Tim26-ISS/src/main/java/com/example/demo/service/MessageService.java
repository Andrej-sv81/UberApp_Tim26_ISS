package com.example.demo.service;

import com.example.demo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
}
