package mijan.letsplay.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

// UserDTO is a data transfer object that will be used to send user data to the client
// It is a POJO (Plain Old Java Object) that contains the same attributes as the User class
// The difference is that it does not contain the password attribute
// This is done for security reasons
// The password attribute should not be sent to the client
// The client should not have access to the user's password
// The client should not be able to see the user's password
// The client should not be able to modify the user's password
// The client should not be able to send the user's password to the server
// The client should not be able to send the user's password to the database
// The client should not be able to send the user's password to anyone
public class UserDTO {
    private String id; // Unique identifier for the user
    private String name; // The user's name
    private String role; // The user's role
}
