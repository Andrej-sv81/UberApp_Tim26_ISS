package com.example.demo.repository;

import com.example.demo.model.RejectionMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RejectionMessageRepository extends JpaRepository<RejectionMessage, Integer> {
    @Query(value = "SELECT * FROM REJECTION_MESSAGE WHERE RIDE_RIDE_ID = ?1", nativeQuery = true)
    RejectionMessage getMessageFromRide(Integer id);
}
