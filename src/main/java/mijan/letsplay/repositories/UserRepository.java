package mijan.letsplay.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import mijan.letsplay.models.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByName(String username);
}
