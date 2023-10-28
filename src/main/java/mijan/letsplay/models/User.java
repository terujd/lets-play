package mijan.letsplay.models;


import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
// User is a model class that represents a user in the database
public class User {
    @Id// The @Id annotation is used to mark the id field as the primary key
    @Builder.Default// The @Builder annotation is used to create a builder class
    // here we are using the @Builder annotation to create a builder class with a default value for the id field
    private String id = uuidGenerator();

    // The @NotBlank annotation is used to validate the name field
    @NotBlank(message = "User name cannot be empty")//
    private String name;

    // The @Email annotation is used to validate the email field
    @NotBlank(message = "User email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    // The @NotBlank annotation is used to validate the password field
    @NotBlank(message = "User password cannot be empty")
    private String password;

    // The @NotBlank annotation is used to validate the role field
    @NotBlank(message = "User role cannot be empty")
    private String role;

    // The setters are used to set the values of the fields
    public void setName(String name) {// The setName() method is used to set the name field
        this.name = name != null ? name.trim() : null;
    }

    public void setEmail(String email) {// The setEmail() method is used to set the email field
        this.email = email != null ? email.trim() : null;
    }

    public void setPassword(String password) {// The setPassword() method is used to set the password field
        this.password = password != null ? password.trim() : null;
    }

    public void setRole(String role) {// The setRole() method is used to set the role field
        this.role = role != null ? role.trim() : null;
    }

    // The uuidGenerator() method is used to generate a unique id for the user
    public static String uuidGenerator() {
        return UUID.randomUUID().toString();
    }
}


// package mijan.letsplay.models;


// import java.util.UUID;

// import org.springframework.data.annotation.Id;
// import org.springframework.data.mongodb.core.mapping.Document;

// import jakarta.validation.constraints.Email;
// import jakarta.validation.constraints.NotBlank;
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Data;
// import lombok.NoArgsConstructor;


// @Data
// @Builder
// @NoArgsConstructor
// @AllArgsConstructor
// @Document(collection = "users")
// public class User {
//     @Id
//     @Builder.Default
//     private String id = uuidGenerator();

//     @NotBlank(message = "User name cannot be empty")
//     private String name;

//     @NotBlank(message = "User email cannot be empty")
//     @Email(message = "Invalid email format")
//     private String email;

//     @NotBlank(message = "User password cannot be empty")
//     //@JsonIgnore
//     private String password;

//     @NotBlank(message = "User role cannot be empty")
//     private String role;
    

//     public void setName(String name) {
//         this.name = name != null ? name.trim() : null;
//     }

//     public void setEmail(String email) {
//         this.email = email != null ? email.trim() : null;
//     }

//     public void setPassword(String password) {
//         this.password = password != null ? password.trim() : null;
//     }

//     public void setRole(String role) {
//         this.role = role != null ? role.trim() : null;
//     }

//     public static String uuidGenerator() {
//         return UUID.randomUUID().toString();
//     }
// }



// import org.springframework.data.annotation.Id;
// import org.springframework.data.mongodb.core.mapping.Document;
// import lombok.Builder; // Import Lombok's @Builder annotation


// @Document
// @Builder // Add the @Builder annotation
// public class User {
//     @Id
//     private String id;
//     private String name;
//     private String email;
//     private String password;
//     private String role;

//     public User() {
//     }

//     public User(String name, String email, String password, String role) {
//         this.name = name;
//         this.email = email;
//         this.password = password;
//         this.role = role;
//     }

//     public User(String id, String name, String email, String password, String role) {
//         this.id = id;
//         this.name = name;
//         this.email = email;
//         this.password = password;
//         this.role = role;
//     }

//     public String getId() {
//         return id;
//     }

//     public String getName() {
//         return name;
//     }

//     public String getEmail() {
//         return email;
//     }

//     public String getPassword() {
//         return password;
//     }

//     public String getRole() {
//         return role;
//     }

//     public void setName(String name) {
//         this.name = name;
//     }

//     public void setEmail(String email) {
//         this.email = email;
//     }

//     public void setPassword(String password) {
//         this.password = password;
//     }

//     public void setRole(String role) {
//         this.role = role;
//     }
// }