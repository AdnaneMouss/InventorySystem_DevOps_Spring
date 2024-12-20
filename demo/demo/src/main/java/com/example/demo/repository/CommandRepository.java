package com.example.demo.repository;

import com.example.demo.model.Command;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandRepository extends JpaRepository<Command, Integer> {
int countByDelivered(boolean delivered);
List<Command> findAllByUserId(int id);

}
