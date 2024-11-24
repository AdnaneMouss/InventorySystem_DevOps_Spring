package com.example.demo.model;
import jakarta.persistence.*;

import java.util.List;

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
    @Column
    private String productionDate ;
    @Column
    private String expiryDate;
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category categorie;

    @OneToMany(mappedBy = "product")
    private List<Command> command;

    public Product(int id, String description, String label, double price, String color, String photo, String size, int quantity, String productionDate, String expiryDate, Category categorie, List<Command> command) {
        this.id = id;
        this.description = description;
        this.label = label;
        this.price = price;
        this.color = color;
        this.photo = photo;
        this.size = size;
        this.quantity = quantity;
        this.productionDate = productionDate;
        this.expiryDate = expiryDate;
        this.categorie = categorie;
        this.command = command;
    }

    public Product() {}


    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

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

    public List<Command> getCommand() {
        return command;
    }

    public void setCommand(List<Command> command) {
        this.command = command;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", color='" + color + '\'' +
                ", photo='" + photo + '\'' +
                ", size='" + size + '\'' +
                ", quantity=" + quantity +
                ", expiryDate='" + expiryDate + '\'' +
                ", categorie=" + categorie +
                ", command=" + command +
                '}';
    }
}
