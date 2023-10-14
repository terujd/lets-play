package mijan.letsplay.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok annotation for generating getter and setter methods
@Builder // Lombok annotation for generating builder pattern
@NoArgsConstructor // Lombok annotation for generating no arguments constructor
@AllArgsConstructor // Lombok annotation for generating all arguments constructor

// to be used for authentication requests to the API (login requests)
public class AuthReq {
    private String email; // The user's email
    private String password; // The user's password
}
