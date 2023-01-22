package com.example.demo.repository;

import com.example.demo.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("select m from Message m where (m.sender.id = ?1) or (m.receiver = ?1)")
    Optional<List<Message>> findAllById(Integer id);
}
