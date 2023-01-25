package com.example.demo.service;

import com.example.demo.model.Panic;
import com.example.demo.repository.PanicRepository;
import com.example.demo.service.interfaces.IPanicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PanicService implements IPanicService {

    @Autowired
    private PanicRepository panicRepository;

    @Override
    public void save(Panic panic) {
        panicRepository.save(panic);
        panicRepository.flush();
    }
}
