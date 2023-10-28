package mijan.letsplay.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import mijan.letsplay.models.User;

// UserRepository is an interface that extends MongoRepository
// MongoRepository is an interface provided by Spring Data MongoDB
// It has methods for performing CRUD operations on the database

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByName(String username);// This method is used to find a user by username

}
