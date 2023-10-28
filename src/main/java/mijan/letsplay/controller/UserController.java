package mijan.letsplay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import mijan.letsplay.exceptions.UserCollectionException;
import mijan.letsplay.models.User;
import mijan.letsplay.models.UserDTO;
import mijan.letsplay.services.UserService;


@RestController
@RequestMapping("/api/users")
//UserController is a REST controller that handles HTTP requests
public class UserController {
    // Inject UserService
    @Autowired
    private UserService userService;

    // Create a new user
    @PostMapping("/create")//create user
    @PreAuthorize("hasRole('ROLE_ADMIN')")//only admin can create user
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);//create user
            UserDTO userDTO = userService.convertToUserDTO(createdUser);//convert user to userDTO
            return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);//return userDTO
        } catch (ConstraintViolationException e) {//if user validation fails
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);//return 422
        } catch (UserCollectionException e) {//if user already exists
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);//return 409
        } catch (Exception e) {//if any other error occurs
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);//return 400
        }
    }

    // Get all users (accessible by all users)/**
/**
 * The function returns a list of UserDTO objects for all users, but it requires the user to have either the 'ROLE_ADMIN' or 'ROLE_USER' role.
 * 
 * @return The method is returning a List of UserDTO objects.
 */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
    public List<UserDTO> getAllUsers() {
        List<UserDTO> userDTOs = userService.getAllUsers(); // Modify this line
        return userDTOs;
    }

/*
 * The function `getUserById` retrieves a user by their ID and returns a `UserDTO` object if found, otherwise it throws a `NotFoundException`.
 *
 * @param id The "id" parameter is a path variable that represents the unique identifier of a user. It is used to retrieve the user with the specified id from the database.
 * @return The method is returning a ResponseEntity object with a generic type of UserDTO.
 */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) throws NotFoundException {
        UserDTO userDTO = userService.getUserById(id);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            throw new NotFoundException();
        }
    }

/*
 * This function updates a user with the given ID and returns a response entity with the result.
 * 
 * @param id The "id" parameter is a path variable that represents the unique identifier of the user to be updated. It is extracted from the URL path.
 * @param user The "user" parameter is of type User and represents the updated user object that will be used to update the user with the specified ID.
 * @return The method is returning a ResponseEntity object.
 */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateUserById(@PathVariable("id") String id, @RequestBody User user) {
        try {
            userService.updateUser(id, user);
            return new ResponseEntity<>("Update User with id " + id, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

// The code is a Java method that handles a DELETE request to delete a user by their ID.
// It is annotated with @DeleteMapping("/{id}") to specify that it handles DELETE requests with a path variable for the user ID.
// It is also annotated with @PreAuthorize("hasRole('ROLE_ADMIN')") to ensure that only users with the "ROLE_ADMIN" role can access this endpoint.
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") String id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>("Successfully deleted user with id " + id, HttpStatus.OK);
        } catch (UserCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}


// package mijan.letsplay.controller;

// import java.util.List;
// import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import jakarta.validation.ConstraintViolationException;
// import jakarta.validation.Valid;
// import mijan.letsplay.exceptions.UserCollectionException;
// import mijan.letsplay.models.User;
// import mijan.letsplay.models.UserDTO;
// import mijan.letsplay.services.UserService;

// @RestController
// @RequestMapping("/api/users")
// public class UserController {
//     @Autowired
//     private UserService userService;


//     @PostMapping
//     @PreAuthorize("hasRole('ROLE_ADMIN')")
//     public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
//         try {
//             userService.createUser(user);
//             return new ResponseEntity<User>(user, HttpStatus.CREATED); // Changed from OK to CREATED (201)
//         } catch (ConstraintViolationException e) {
//             return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
//         } catch (UserCollectionException e) {
//             return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
//         } catch (Exception e) {
//             return new ResponseEntity<>("An error occurred", HttpStatus.BAD_REQUEST);
//         }
//     }


//     // Get all users (accessible by all users)
//     @GetMapping("/all")
//     @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
//     public List<UserDTO> getAllUsers() {
//         List<User> users = userService.getAllUsers();
//         return users.stream().map(this::convertToDTO).collect(Collectors.toList());
//     }

//     // Get a specific user by id
//     @GetMapping("/{id}")
//     @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
//     public ResponseEntity<UserDTO> getUserById(@PathVariable String id) throws NotFoundException {
//         User user = userService.getUserById(id);
//         if (user != null) {
//             UserDTO userDTO = convertToDTO(user);
//             return ResponseEntity.ok(userDTO);
//         } else {
//             throw new NotFoundException();
//         }
//     }

//     // Update User
//     @PutMapping("/{id}")
//     @PreAuthorize("hasRole('ROLE_ADMIN')")
//     public ResponseEntity<?> updateUserById(@PathVariable("id") String id, @RequestBody User user) {
//         try {
//             userService.updateUser(id, user);
//             return new ResponseEntity<>("Update User with id " + id, HttpStatus.OK);
//         } catch (ConstraintViolationException e) {
//             return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
//         } catch (Exception e) {
//             return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//         }
//     }

//     // Rest of your UserController methods...
    
//     private UserDTO convertToDTO(User user) {
//         UserDTO userDTO = new UserDTO();
//         userDTO.setId(user.getId());
//         userDTO.setName(user.getName());
//         userDTO.setRole(user.getRole());
//         return userDTO;
//     }
// }

// package mijan.letsplay.controller;

// import java.security.NoSuchAlgorithmException;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import jakarta.validation.ConstraintViolationException;
// import mijan.letsplay.exceptions.UserCollectionException;
// import mijan.letsplay.models.User;
// import mijan.letsplay.services.UserService;


// @RestController
// @RequestMapping("/api/users")
// public class UserController {
//     @Autowired
//     private UserService userService;

//         @Autowired
//     private PasswordEncoder passwordEncoder; // Inject PasswordEncoder

//     // Create a new user
//     @PostMapping("/create")
//     @PreAuthorize("hasRole('ROLE_ADMIN')")
//     public User createUser(@RequestBody User user) throws UserCollectionException, NotFoundException {
//             // Hash the password using BCryptPasswordEncoder
//         String hashedPassword = passwordEncoder.encode(user.getPassword());
//         user.setPassword(hashedPassword);
//         return userService.createUser(user);
//     }

//     // Get all users (accessible by all users)
//     @GetMapping("/all")
//     @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
//     public List<User> getAllUsers() {
//         return userService.getAllUsers();
//     }

//     // Get a specific user by id
//     @GetMapping("/{id}")
//     @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
//     public ResponseEntity<User> getUserById(@PathVariable String id) throws NotFoundException {
//         User user = userService.getUserById(id);
//         if (user == null) {
//             return ResponseEntity.ok(user);
//         } else {
//             throw new NotFoundException();
//         }
//     }

//     // Update a user
//     @PutMapping("/update")
//     @PreAuthorize("hasRole('ROLE_ADMIN')")
//     public User updateUser(@RequestBody User user) throws NotFoundException, UserCollectionException, ConstraintViolationException, NoSuchAlgorithmException {
//             // Check if the user is updating the password
//     if (user.getPassword() != null) {
//         // Hash the new password using BCryptPasswordEncoder
//         String hashedPassword = passwordEncoder.encode(user.getPassword());
//         user.setPassword(hashedPassword);
//     }

//         return userService.updateUser(null, user);
//     }

//     // Delete a user
//     @DeleteMapping("/delete/{id}")
//     @PreAuthorize("hasRole('ROLE_ADMIN')")
//     public void deleteUser(@PathVariable String id) throws NotFoundException, UserCollectionException {
//         userService.deleteUser(id);
//     }
// }