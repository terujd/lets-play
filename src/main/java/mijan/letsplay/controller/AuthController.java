package mijan.letsplay.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mijan.letsplay.models.AuthRequest;
import mijan.letsplay.models.User;
import mijan.letsplay.repositories.UserRepository;
import mijan.letsplay.services.JWTService;

@RestController
@RequestMapping("/api/auth")//the base url for the auth controller
// AuthController is a controller class that will be used to handle requests to the /api/auth endpoint.
public class AuthController {

    // The JWTService bean is used to generate and validate the JWT
    @Autowired
    private JWTService jwtService;

    // The UserRepository bean is used to perform CRUD operations on the User collection.
    @Autowired
    private UserRepository userRepository;

    // The PasswordEncoder bean is used to encode the password
    @Autowired
    private PasswordEncoder passwordEncoder;

    // The authenticateAndGetToken method is used to authenticate the user and generate the JWT.
    @PostMapping
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            // Basic input validation to prevent injection attacks
            if (authRequest.getUsername() == null || authRequest.getPassword() == null) {//check if the username and password are null
                return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);//return a bad request message
            }

            Optional<User> user = userRepository.findByName(authRequest.getUsername());//find the user by username
            if (user.isPresent()) {//check if the user is present
                if (passwordEncoder.matches(authRequest.getPassword(), user.get().getPassword())) {//check if the password matches
                    String token = jwtService.generateToken(user.get().getName());//generate the token
                    return new ResponseEntity<>(token, HttpStatus.OK);//return a success message
                } else {
                    return new ResponseEntity<>("Wrong password", HttpStatus.UNAUTHORIZED);//return an unauthorized message
                }
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);//return a not found message
            }
        } catch (Exception e) {
            // Generic catch block to ensure no 5XX errors are returned
            return new ResponseEntity<>("Definitely Not An Internal Server Error, It Is Just A Bad Request",
                    HttpStatus.BAD_REQUEST);
        }
    }
}



// package mijan.letsplay.controller;

// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.crypto.password.PasswordEncoder; // Add this import
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import mijan.letsplay.models.AuthRequest;
// import mijan.letsplay.models.User;
// import mijan.letsplay.repositories.UserRepository;
// import mijan.letsplay.services.JWTService;

// @RestController
// @RequestMapping("/api/auth")
// public class AuthController {

//     @Autowired
//     private JWTService jwtService;

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private PasswordEncoder passwordEncoder; // Add this line

//     @PostMapping
//     public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
//         try {
//             // Basic input validation to prevent injection attacks
//             if (authRequest.getUsername() == null || authRequest.getPassword() == null) {
//                 return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
//             }

//             Optional<User> user = userRepository.findByName(authRequest.getUsername());
//             if (user.isPresent()) {
//                 if (passwordEncoder.matches(authRequest.getPassword(), user.get().getPassword())) {
//                     String token = jwtService.generateToken(user.get().getName());
//                     return new ResponseEntity<>(token, HttpStatus.OK);
//                 }
//             }

//             // If authentication fails or user not found, return an unauthorized status
//             return new ResponseEntity<>("Authentication failed", HttpStatus.UNAUTHORIZED);
//         } catch (Exception e) {
//             // Generic catch block to ensure no 5XX errors are returned
//             return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
//         }
//     }
// }
