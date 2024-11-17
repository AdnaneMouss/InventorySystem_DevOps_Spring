package com.example.demo.repository;

import com.example.demo.model.Command;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandRepository extends JpaRepository<Command, Integer> {
    // Des méthodes personnalisées peuvent être ajoutées ici si nécessaire
}
