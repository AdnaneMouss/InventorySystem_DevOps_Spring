package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  CompteRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmailAndPassword(String email, String password);
    List<User> findAllByType(String type);
}
