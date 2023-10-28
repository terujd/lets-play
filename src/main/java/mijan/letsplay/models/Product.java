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

// Product is a POJO class that represents a product in the database
// It has the following fields:
// id: a unique identifier for the product
// name: the product's name
// description: the product's description
// price: the product's price
// userId: the user who created the product
// The Product class is annotated with the @Document annotation
// The @Document annotation is used to indicate that the class is a document that will be stored in the database
// The @Document annotation is provided by Spring Data MongoDB
// The @Document annotation has a collection attribute that is used to specify the name of the collection where the documents will be stored
// In this case, the collection name is products
public class Product {

    @Builder.Default // The @Builder annotation is used to create a builder class for the Product class
    @Id // The @Id annotation is used to mark the id field as the primary key
    private String id = uuidGenerator();

    // The @NotBlank annotation is used to validate the name field
    @NotBlank(message = "Product name cannot be empty")
    private String name;

    // The @NotBlank annotation is used to validate the description field
    @NotBlank(message = "Product description cannot be empty")
    private String description;

    // The @NotBlank annotation is used to validate the price field
    // The @DecimalMin annotation is used to validate the price field
    @NotBlank(message = "Product price cannot be null")
    @DecimalMin(value = "0.0", message = "Product price cannot be less than 0")
    private double price; // Use a numerical data type for prices

    // The @NotBlank annotation is used to validate the userId field
    @NotBlank(message = "Product userId cannot be empty")
    private String userId;


    // The setters are used to set the values of the fields
    public void setUserId(String userId) {
        this.userId = userId != null ? userId.trim() : null;
    }

    // The uuidGenerator() method is used to generate a unique id for the product
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