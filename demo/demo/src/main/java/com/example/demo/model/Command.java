package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name ="commands")
public class Command {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idcommand;

    @ManyToOne
    @JoinColumn(name = "supplier")
    private User user;


    @Column
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column
    private int quantity;

    @Column
    private String deliveryDate;

    @Column
    private boolean delivered;

    public Command(int idcommand, User user, LocalDateTime creationDate, Product product, boolean delivered) {
        this.idcommand = idcommand;
        this.user = user;
        this.creationDate = creationDate;
        this.product = product;
        this.delivered = delivered;
    }

    public Command() {

    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return idcommand;
    }

    public void setId(int id) {
        this.idcommand = idcommand;
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
                "idcommand=" + idcommand +
                ", user=" + user +
                ", creationDate=" + creationDate +
                ", product=" + product +
                ", delivered=" + delivered +
                '}';
    }
}

