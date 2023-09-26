package mijan.letsplay.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import mijan.letsplay.models.User;

public interface UserRepository extends MongoRepository<User, String>{ 
    User findByEmail(String email);
}