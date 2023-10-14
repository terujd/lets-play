package mijan.letsplay.exceptions;

import java.io.Serial;

// This class is used to throw exceptions
public class ProductCollectionException extends Exception {
    /**
     *
     */
    @Serial
    // This is a serializable class
    private static final long serialVersionUID = 1L;

    // This is a constructor
    public ProductCollectionException(String message) {
        super(message);
    }

    // This method is used to generate a random UUID
    public static String NotFoundException(String id) {
        return "Product with " + id + " not found!";
    }

    public static String ProductAlreadyExistException() {
        return "Product already exist!";
    }

    public static String NoChangesMadeException() {
        return "No changes to update!";
    }

    public static String UserNotFoundException() {
        return "User not found!";
    }

    public static String NullException() {
        return " cannot be empty!";
    }

}