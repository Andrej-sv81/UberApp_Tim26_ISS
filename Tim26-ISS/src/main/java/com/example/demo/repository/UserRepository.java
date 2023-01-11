package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

        public User findOneById(int id);
        public User findOneByEmail(String email);
        public Page<User> findAll(Pageable pageable);
        public List<User> findByNameAndSurnameAllIgnoringCase(String name, String surname);
        @Query("select u from User u where u.name = ?1")
        public List<User> findUserByName(String name);
}
