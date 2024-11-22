package com.example.demo.model;
import jakarta.persistence.*;
@Entity
@Table(name ="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String label;
    @Column
    private String description;
    @Column
    private double price;
    @Column
    private String color;
    @Column
    private String photo;
    @Column
    private String size;
    @Column
    private int quantity;
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category categorie;

    @ManyToOne
    @JoinColumn(name = "id_command")
    private Command command;

    public Product(int id, String label, String description, double price, String color, String photo, int quantity, String size, Category categorie) {
        this.id = id;
        this.label = label;
        this.description = description;
        this.price = price;
        this.color = color;
        this.photo = photo;
        this.size = size;
        this.categorie = categorie;
        this.quantity=quantity;
    }

    public Product() {}


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Category getCategorie() {
        return categorie;
    }

    public void setCategorie(Category categorie) {
        this.categorie = categorie;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }
}
