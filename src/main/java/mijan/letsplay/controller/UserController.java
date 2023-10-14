package mijan.letsplay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mijan.letsplay.models.User;
import mijan.letsplay.services.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    // Create a new user
    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // Get all users (accessible by all users)
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get a specific user by id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) throws NotFoundException {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.ok(user);
        } else {
            throw new NotFoundException();
        }
    }

    // Update a user
    @PutMapping("/update")
    public User updateUser(@RequestBody User user) throws NotFoundException {
        return userService.updateUser(user);
    }

    // Delete a user
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable String id) throws NotFoundException {
        userService.deleteUser(id);
    }
}