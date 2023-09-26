package mijan.letsplay.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private String price;
    private String userId; // the user who created this product

    public Product() {
    }

    public Product(String name, String description, String price, String userId) {
        this.name        = name;
        this.description = description;
        this.price       = price;
        this.userId      = userId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesctiption() {
        return description;
    }

    public String getPrice() {
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

    public void setPrice(String price) {
        this.price = price;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDesctiption(String string) {
    }
}