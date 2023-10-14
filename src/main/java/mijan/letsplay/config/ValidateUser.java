package mijan.letsplay.config;

import java.util.regex.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import mijan.letsplay.exceptions.UserCollectionException;
import mijan.letsplay.models.User;
import mijan.letsplay.repositories.UserRepository;

public class ValidateUser {
    // Define a method to validate a user object.
    public static void validateUser(User user) throws UserCollectionException {
        if (user == null) {
            // Throw an exception if the user object is null.
            throw new UserCollectionException("User object is null.");
        }

        // Validate and trim user name
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            // Throw an exception if the name is null or empty.§
            throw new UserCollectionException("User name is required.");
        } else if (user.getName().trim().length() > 50) {
            // Throw an exception if the name is too long.
            throw new UserCollectionException("User name is too long (max 50 characters).");
        } else {
            // Trim the name and store it in the database.
            user.setName(user.getName().trim());
            System.out.println("User name: " + user.getName());
        }

        // Validate and trim user email
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            // Throw an exception if the email is null or empty.
            throw new UserCollectionException("User email is required.");
        } else if (!isValidEmail(user.getEmail())) {
            // Throw an exception if the email is invalid.
            throw new UserCollectionException("Invalid email format.");
        } else if (emailAlreadyExists(user.getEmail())) {
            // Throw an exception if the email is already registered.
            throw new UserCollectionException("Email is already registered.");
        } else {
            // Trim the email and store it in the database.
            user.setEmail(user.getEmail().trim());
            // Hash the email and store the hash in the database.
            System.out.println("User email: " + user.getEmail());
        }

        // Validate and trim user password
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            // Throw an exception if the password is null or empty.
            throw new UserCollectionException("User password is required.");
        } else if (user.getPassword().trim().length() < 6) {
            // Throw an exception if the password is too short.
            throw new UserCollectionException("Password must be at least 6 characters long.");
        } else if (!isStrongPassword(user.getPassword())) {
            // Throw an exception if the password is not strong.
            throw new UserCollectionException("Password does not meet complexity requirements.");
        } else {
            // Trim the password and store it in the database.
            user.setPassword(user.getPassword().trim());
            // Hash the password and store the hash in the database.
            System.out.println("User password: " + user.getPassword());
        }

        // Check user role (assuming 'role' is an enum)
        if (user.getRole() == null || !isValidRole(user.getRole())) {
            // Throw an exception if the role is invalid.
            throw new UserCollectionException("Invalid user role.");
        }

    }
    private static boolean isValidRole(String role) {
        // Return true if the role is valid, false otherwise.
        if (role == null) {
            return false;
        }
        return role.equals("admin") || role.equals("user");
    }

    // Define a PasswordEncoder object to hash passwords.
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static boolean isStrongPassword(String password) {
        // Define the regex to check for a strong password.
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).+$";
        
        // Check if the password matches the regex.
        boolean meetsComplexityRequirements = Pattern.compile(passwordRegex).matcher(password).matches();

        // Hash the password and compare it to the original.
        String storedHashedPassword = passwordEncoder.encode(password);

        // Return true if the password is strong, false otherwise.
        boolean isStrong = meetsComplexityRequirements && password.equals(storedHashedPassword);
        return isStrong;
    }

    private static boolean emailAlreadyExists(String email) {
        // Return true if the email is already registered, false otherwise.
        User user = UserRepository.findByEmail(email);
        return user != null;
    }

    private static boolean isValidEmail(String email) {
        // Return true if the email is valid, false otherwise.
        // Define the regex to check for a valid email.
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
        // Compile the regex.
        Pattern pattern = Pattern.compile(emailRegex);
        // Check if the email matches the regex.
        return pattern.matcher(email).matches();
    }
}