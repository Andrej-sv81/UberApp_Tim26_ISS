package com.example.demo.service;

import com.example.demo.model.Message;
import com.example.demo.repository.MessageRepository;
import com.example.demo.service.interfaces.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MessageService implements IMessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public List<Message> findAllBySenderId(Integer id) {
        return  messageRepository.findAllBySenderId(id);
    }

    @Override
    public void save(Message message) {
        messageRepository.save(message);
    }
}
