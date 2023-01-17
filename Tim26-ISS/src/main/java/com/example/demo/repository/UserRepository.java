package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findOneByEmail(String email);
    List<User> findByNameAndSurnameAllIgnoringCase(String name, String surname);
    @Query("select u from User u where u.name = ?1")
    List<User> findUserByName(String name);
    User findOneById(Integer id);

}
