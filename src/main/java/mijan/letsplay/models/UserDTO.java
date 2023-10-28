package mijan.letsplay.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserDTO {
    private String id; // Unique identifier for the user
    private String name; // The user's name
    private String role; // The user's role
}
