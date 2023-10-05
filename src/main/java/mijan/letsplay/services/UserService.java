package mijan.letsplay.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mijan.letsplay.exceptions.NotFoundException;
import mijan.letsplay.models.User;
import mijan.letsplay.repositories.UserRepository;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        // Add Validation logic if needed
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();    
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with ID: " + id));
    }

    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId()).orElseThrow(() -> new NotFoundException("User not found with ID: " + user.getId()));
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        return userRepository.save(existingUser);
    }

    public void deleteUser(String id) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with ID: " + id));
        userRepository.delete(existingUser);
    }

}