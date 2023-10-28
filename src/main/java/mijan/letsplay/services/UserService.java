package mijan.letsplay.services;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.ConstraintViolationException;
import mijan.letsplay.config.ValidateUser;
import mijan.letsplay.exceptions.UserCollectionException;
import mijan.letsplay.models.Product;
import mijan.letsplay.models.User;
import mijan.letsplay.models.UserDTO;
import mijan.letsplay.repositories.ProductRepository;
import mijan.letsplay.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user) throws UserCollectionException {
        validateUser(user);
        if (userRepository.existsById(user.getName())) {
            throw new UserCollectionException("User already exists.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToUserDTO).collect(Collectors.toList());
    }

    public UserDTO getUserById(String id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException());
        return convertToUserDTO(user);
    }

    public UserDTO convertToUserDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getRole());
    }

    public User updateUser(String id, User updatedUser)
            throws ConstraintViolationException, UserCollectionException, NoSuchAlgorithmException {
        ValidateUser.validateUser(updatedUser);
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserCollectionException(UserCollectionException.NotFoundException(id));
        }
        updatedUser.setId(id);
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        return userRepository.save(updatedUser);
    }

    public void deleteUser(String id) throws UserCollectionException {
        Optional<User> userOptional = userRepository.findById(id);

        if (!userOptional.isPresent()) {
            throw new UserCollectionException(UserCollectionException.NotFoundException(id));
        }

        User user = userOptional.get();

        // Find all products associated with the user and delete them
        List<Product> productsToDelete = productRepository.findByUserId(id);

        // You can use the forEach method to delete products
        productsToDelete.forEach(productRepository::delete);

        // Now delete the user
        userRepository.delete(user);
    }

    private void validateUser(User user) throws UserCollectionException {
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new UserCollectionException("User name cannot be null or empty.");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new UserCollectionException("Email cannot be null or empty.");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new UserCollectionException("Password cannot be null or empty.");
        }
    }
}

// import java.security.NoSuchAlgorithmException;
// import java.util.List;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import jakarta.validation.ConstraintViolationException;
// import mijan.letsplay.config.ValidateUser;
// import mijan.letsplay.exceptions.UserCollectionException;
// import mijan.letsplay.models.Product;
// import mijan.letsplay.models.User;
// import mijan.letsplay.models.UserDTO; // Import UserDTO
// import mijan.letsplay.repositories.ProductRepository;
// import mijan.letsplay.repositories.UserRepository;

// @Service
// public class UserService {
//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private ProductRepository productRepository;

//     @Autowired
//     private PasswordEncoder passwordEncoder;

//     public User createUser(User user) throws UserCollectionException {
//         validateUser(user);
//         if (userRepository.existsById(user.getName())) {
//             throw new UserCollectionException("User already exists.");
//         }

//         user.setPassword(passwordEncoder.encode(user.getPassword()));
//         return userRepository.save(user);
//     }

//     @Transactional
//     public List<User> getAllUsers() {
//         return userRepository.findAll();
//     }

//     public User getUserById(String id) throws NotFoundException {
//         return userRepository.findById(id).orElseThrow(() -> new NotFoundException());
//     }

//     // public UserDTO convertToDTO(User user) {
//     //     // Create a UserDTO from a User
//     //     return UserDTO.builder()
//     //         .id(user.getId())
//     //         .name(user.getName())
//     //         .role(user.getRole())
//     //         .build();
//     // }
//     // Conversion Method
//     public UserDTO convertToUserDTO(User user) {
//         return new UserDTO(user.getId(), user.getName(), user.getRole());
//     }

//     // Update User
//     public User updateUser(String id, User updatedUser)
//             throws ConstraintViolationException, UserCollectionException, NoSuchAlgorithmException {
//         ValidateUser.validateUser(updatedUser);
//         Optional<User> userOptional = userRepository.findById(id);
//         if (userOptional.isEmpty()) {
//             throw new UserCollectionException(UserCollectionException.NotFoundException(id));
//         }
//         updatedUser.setId(id);
//         updatedUser.setName(updatedUser.getName());
//         updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
//         updatedUser.setRole(updatedUser.getRole());
//         updatedUser.setEmail(updatedUser.getEmail());

//         return userRepository.save(updatedUser);
//     }

//     public void deleteUser(String id) throws UserCollectionException {
//         Optional<User> userOptional = userRepository.findById(id);

//         if (!userOptional.isPresent()) {
//             throw new UserCollectionException(UserCollectionException.NotFoundException(id));
//         }

//         User user = userOptional.get();

//         // Find all products associated with the user and delete them
//         List<Product> productsToDelete = productRepository.findByUserId(id);
//         System.out.println("Products to delete: " + productsToDelete);

//         // You can use the forEach method to delete products
//         productsToDelete.forEach(product -> productRepository.delete(product));

//         // Now delete the user
//         userRepository.delete(user);
//     }

//     private void validateUser(User user) throws UserCollectionException {
//         if (user.getName() == null || user.getName().isEmpty()) {
//             throw new UserCollectionException("User name cannot be null or empty.");
//         }
//         if (user.getEmail() == null || user.getEmail().isEmpty()) {
//             throw new UserCollectionException("Email cannot be null or empty.");
//         }
//         if (user.getPassword() == null || user.getPassword().isEmpty()) {
//             throw new UserCollectionException("Password cannot be null or empty.");
//         }
//     }
// }



// import java.security.NoSuchAlgorithmException;
// import java.util.List;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import jakarta.validation.ConstraintViolationException;
// import mijan.letsplay.config.ValidateUser;
// import mijan.letsplay.exceptions.UserCollectionException;
// import mijan.letsplay.models.Product;
// import mijan.letsplay.models.User;
// import mijan.letsplay.repositories.ProductRepository;
// import mijan.letsplay.repositories.UserRepository;

// @Service
// public class UserService {
//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private ProductRepository productRepository; // Inject ProductRepository

//     @Autowired
//     private PasswordEncoder passwordEncoder;

//     public User createUser(User user) throws UserCollectionException {
//         validateUser(user);
//         if (userRepository.existsById(user.getName())) {
//             throw new UserCollectionException("User already exists.");
//         }

//         user.setPassword(passwordEncoder.encode(user.getPassword()));
//         return userRepository.save(user);
//     }

//     @Transactional
//     public List<User> getAllUsers() {
//         return userRepository.findAll();
//     }

//     public User getUserById(String id) throws NotFoundException {
//         return userRepository.findById(id).orElseThrow(() -> new NotFoundException());
//     }

//     // Update User
//     public User updateUser(String id, User updatedUser)
//             throws ConstraintViolationException, UserCollectionException, NoSuchAlgorithmException {
//         ValidateUser.validateUser(updatedUser);
//         Optional<User> userOptional = userRepository.findById(id);
//         if (userOptional.isEmpty()) {
//             throw new UserCollectionException(UserCollectionException.NotFoundException(id));
//         }
//         updatedUser.setId(id);
//         updatedUser.setName(updatedUser.getName());
//         updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
//         updatedUser.setRole(updatedUser.getRole());
//         updatedUser.setEmail(updatedUser.getEmail());

//         return userRepository.save(updatedUser);

//     }

//     public void deleteUser(String id) throws UserCollectionException {
//         Optional<User> userOptional = userRepository.findById(id);
        
//         if (!userOptional.isPresent()) {
//             throw new UserCollectionException(UserCollectionException.NotFoundException(id));
//         }
    
//         User user = userOptional.get();
    
//         // Find all products associated with the user and delete them
//         List<Product> productsToDelete = productRepository.findByUserId(id);
//         System.out.println("Products to delete: " + productsToDelete);
        
//         // You can use the forEach method to delete products
//         productsToDelete.forEach(product -> productRepository.delete(product));
    
//         // Now delete the user
//         userRepository.delete(user);
//     }

//     private void validateUser(User user) throws UserCollectionException {
//         if (user.getName() == null || user.getName().isEmpty()) {
//             throw new UserCollectionException("User name cannot be null or empty.");
//         }
//         if (user.getEmail() == null || user.getEmail().isEmpty()) {
//             throw new UserCollectionException("Email cannot be null or empty.");
//         }
//         if (user.getPassword() == null || user.getPassword().isEmpty()) {
//             throw new UserCollectionException("Password cannot be null or empty.");
//         }
//     }
// }