package mijan.letsplay.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

// AuthRequest is a data transfer object that will be used to send user credentials to the server
// It is a POJO (Plain Old Java Object) that contains the username and password attributes
// The username and password attributes are used to authenticate the user
public class AuthRequest {
    private String username;
    private String password;
}
