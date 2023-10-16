package mijan.letsplay.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mongodb.MongoException;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle ConstraintViolationException
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        // Provides more detailed error messages or validation error details if needed
        return new ResponseEntity<>("Validation failed: " + e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    // Handle MongoException
    @ExceptionHandler(MongoException.class)
    public ResponseEntity<String> handleMongoException(MongoException e) {
        // Log the MongoDB error for internal tracking (can use a logger like SLF4J or log4j)
        System.err.println("MongoDB Error: " + e.getMessage());

        // Return HTTP status and error message
        return new ResponseEntity<>("Service temporarily unavailable", HttpStatus.SERVICE_UNAVAILABLE);
    }
}