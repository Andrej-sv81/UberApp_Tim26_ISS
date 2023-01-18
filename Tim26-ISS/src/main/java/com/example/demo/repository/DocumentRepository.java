package com.example.demo.repository;

import com.example.demo.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Integer> {

    List<Document> findAll();

    Document findOneById(Integer id);

    @Query ("select d.documents from Driver d where d.id=?1")
    List<Document> getDocuments(Integer id);
}
