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

    @ManyToMany
    @JoinTable(
            name = "command_product",
            joinColumns = @JoinColumn(name = "idcommand"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private List<Product> product;
    @Column
    private int quantity;

    @Column
    private String deliveryDate;

    @Column
    private boolean delivered;



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


    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public Command(int idcommand, User user, LocalDateTime creationDate, List<Product> products, int quantity, String deliveryDate, boolean delivered) {
        this.idcommand = idcommand;
        this.user = user;
        this.creationDate = creationDate;
        this.product = product;
        this.quantity = quantity;
        this.deliveryDate = deliveryDate;
        this.delivered = delivered;
    }

    public int getIdcommand() {
        return idcommand;
    }

    public void setIdcommand(int idcommand) {
        this.idcommand = idcommand;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> products) {
        this.product = products;
    }
}

