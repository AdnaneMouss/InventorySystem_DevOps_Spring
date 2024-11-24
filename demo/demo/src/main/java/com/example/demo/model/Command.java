package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name ="commands")
public class Command {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "supplier")
    private User user;


    @Column
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column
    private boolean delivered;

    public Command(int id, User user, LocalDateTime creationDate, Product product, boolean delivered) {
        this.id = id;
        this.user = user;
        this.creationDate = creationDate;
        this.product = product;
        this.delivered = delivered;
    }

    public Command() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    @Override
    public String toString() {
        return "Command{" +
                "id=" + id +
                ", user=" + user +
                ", creationDate=" + creationDate +
                ", product=" + product +
                ", delivered=" + delivered +
                '}';
    }
}

