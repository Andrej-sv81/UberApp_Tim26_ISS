package com.example.demo.service;

import com.example.demo.exceptions.UserDoesNotExistException;
import com.example.demo.model.Message;
import com.example.demo.model.User;
import com.example.demo.repository.MessageRepository;
import com.example.demo.service.interfaces.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService implements IMessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public List<Message> findAllById(Integer id) {
        Optional<List<Message>> found = messageRepository.findAllById(id);
        if (found.isEmpty()) {
            return null;
        }
        return found.get();
    }

    @Override
    public void save(Message message) {
        messageRepository.save(message);
    }
}
