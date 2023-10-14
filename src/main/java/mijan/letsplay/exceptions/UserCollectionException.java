package mijan.letsplay.exceptions;

// This class is used to throw exceptions
public class UserCollectionException extends Exception {
    /**
     *
     */
    // This is a serializable class
    private static final long serialVersionUID = 1L;

    // This is a constructor
    public UserCollectionException(String message) {
        super(message);
    }

    // This method is used to generate a random UUID
    public static String NotFoundException(String id) {
        return "User with " + id + " not found!";
    }

    public static String UserAlreadyExistException() {
        return "User already exist!";
    }

    public static String NoChangesMadeException() {
        return "No changes to update!";
    }

    public static String NullException() {
        return " cannot be empty!";
    }
}
