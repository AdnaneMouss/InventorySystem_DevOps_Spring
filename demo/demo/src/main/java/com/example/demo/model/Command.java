package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Command {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_command;

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

    @OneToMany(mappedBy = "command", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    private LocalDateTime creationDate;

    // Getters et setters
    public int getId_command() {
        return id_command;
    }

    public void setId_command(int id_command) {
        this.id_command = id_command;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    // Constructeurs
    public Command() {
    }

    public Command(int id_command, User user, List<Product> products, LocalDateTime creationDate) {
        this.id_command = id_command;
        this.user = user;
        this.products = products;
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Command{" +
                "id_command=" + id_command +
                ", user=" + user +
                ", products=" + products +
                ", creationDate=" + creationDate +
                '}';
    }
}
