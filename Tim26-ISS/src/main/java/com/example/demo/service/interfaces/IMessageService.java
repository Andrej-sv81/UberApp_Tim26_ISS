package com.example.demo.service.interfaces;

import com.example.demo.model.Message;

import java.util.List;

public interface IMessageService {

    List<Message> findAllById(Integer id);

    void save(Message message);
}
