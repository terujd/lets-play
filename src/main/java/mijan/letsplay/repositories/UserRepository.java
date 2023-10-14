package mijan.letsplay.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mijan.letsplay.models.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
    static User findByEmail(String email) {
        throw new UnsupportedOperationException("Unimplemented method 'findByEmail'");
    }

    static User findByUsername(String username) {
        throw new UnsupportedOperationException("Unimplemented method 'findByUsername'");
    }
}