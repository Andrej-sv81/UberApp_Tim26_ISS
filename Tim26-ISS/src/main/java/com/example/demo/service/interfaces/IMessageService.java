package com.example.demo.service.interfaces;

import com.example.demo.model.Message;

import java.util.List;

public interface IMessageService {

    List<Message> findAllBySenderId(Integer id);

    void save(Message message);
}
