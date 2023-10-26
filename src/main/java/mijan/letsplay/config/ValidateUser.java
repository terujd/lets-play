package mijan.letsplay.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mijan.letsplay.exceptions.UserCollectionException;
import mijan.letsplay.models.User;

// Validate the user's data
public class ValidateUser {
    public static void validateUser(User user) throws UserCollectionException {
        // Check and trim the user's name
        if (user.getName() != null) {
            user.setName(user.getName().trim());
        } else {
            throw new UserCollectionException("User name" + UserCollectionException.NullException());
        }
        // Check and trim the user's email and validate its format
        if (user.getEmail() != null) {
            boolean isValid = isValidEmail(user.getEmail());
            System.out.println(user.getEmail() + " is valid email: " + isValid);
            if (!isValid) {
                throw new UserCollectionException(UserCollectionException.InvalidEmailException());
            }
            user.setEmail(user.getEmail().trim());
        } else {
            throw new UserCollectionException("User email" + UserCollectionException.NullException());
        }

        // Check and trim the user's password
        if (user.getPassword() != null) {
            user.setPassword(user.getPassword().trim()); // Trim the ID field as well
        } else {
            throw new UserCollectionException("User password" + UserCollectionException.NullException());
        }

        // Check if role enum is either user.getRole() is "ROLE_ADMIN" or "ROLE_USER"
        if (user.getRole() != null) {
            
            if (!(user.getRole().equals("ROLE_ADMIN") || user.getRole().equals("ROLE_USER"))) {
                throw new UserCollectionException("User role" + UserCollectionException.InvalidRoleException());
            }
            user.setRole(user.getRole().trim());
        } else {
            throw new UserCollectionException("User role" + UserCollectionException.NullException());
        }

    }

    // This method is used to validate the email address
    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    // Validate an email against the defined regex pattern
    public static boolean isValidEmail(String email) {
        // Compile the regex pattern
        Pattern pattern = Pattern.compile(EMAIL_REGEX);

        // Match the input email against the pattern
        Matcher matcher = pattern.matcher(email);

        // Return true if it matches the pattern (valid format), false otherwise
        return matcher.matches();
    }
}
