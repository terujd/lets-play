package mijan.letsplay.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

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

    public User getUserById(String id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException());
    }

    public User updateUser(User user) throws NotFoundException {
        User existingUser = userRepository.findById(user.getId()).orElseThrow(() -> new NotFoundException());
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        return userRepository.save(existingUser);
    }

    public void deleteUser(String id) throws NotFoundException {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException());
        userRepository.delete(existingUser);
    }

}