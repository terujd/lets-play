package mijan.letsplay.models;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")

public class Product {
    @Builder.Default
    @Id
    private String id = uuidGenerator();

    @NotBlank(message = "Product name cannot be empty")
    private String name;

    @NotBlank(message = "Product description cannot be empty")
    private String description;

    @NotBlank(message = "Product price cannot be null")
    @DecimalMin(value = "0.0", message = "Product price cannot be less than 0")
    private double price;

    @NotBlank(message = "Product userId cannot be empty")
    private String userId;

    public void setUserId(String userId) {
        this.userId = userId != null ? userId.trim() : null;
    }

    public static String uuidGenerator() {
        return UUID.randomUUID().toString();
    }
}


// @Document
// public class Product {
//     @Id
//     private String id; // Use a string data type for the id
//     private String name; // Use a string data type for the name
//     private String description; // Use a text data type for longer strings
//     private double price; // Use a numerical data type for prices
//     private String userId; // the user who created this product


//     public Product(String name, String description, double price, String userId) {
//         this.name = name;
//         this.description = description;
//         this.price = price;
//         this.userId = userId;
//     }

//     public String getId() {
//         return id;
//     }

//     public String getName() {
//         return name;
//     }

//     public String getDescription() {
//         return description;
//     }

//     public double getPrice() {
//         return price;
//     }

//     public String getUserId() {
//         return userId;
//     }

//     public void setName(String name) {
//         this.name = name;
//     }

//     public void setId(Object uuidGenerator) {
//         return;
//     }

//     public void setDescription(String description) {
//         this.description = description;
//     }

//     public void setPrice(double price) {
//         this.price = price;
//     }

//     public void setUserId(String userId) {
//         this.userId = userId;
//     }

//     public void setDesctiption(String string) {
//     }

//     public void setPrice(String string) {
//     }

//     public static Object uuidGenerator() {
//         return null;
//     }
// }