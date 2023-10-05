package mijan.letsplay.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mijan.letsplay.models.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{ 
    User findByEmail(String email);
}