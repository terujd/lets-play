package mijan.letsplay.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private double price; // Use a numerical data type for prices
    private String userId; // the user who created this product

    public Product() {
    }

    public Product(String name, String description, double price, String userId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getUserId() {
        return userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDesctiption(String string) {
    }

    public void setPrice(String string) {
    }
}