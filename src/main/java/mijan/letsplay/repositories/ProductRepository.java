package mijan.letsplay.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import mijan.letsplay.models.Product;

// ProductRepository is an interface that extends MongoRepository
// MongoRepository is an interface provided by Spring Data MongoDB
// It has methods for performing CRUD operations on the database
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    // herre we are using the @Query annotation to define a custom query to find all the products that belong to a user.
    @Query("{'userId': ?0}")
    List<Product> findByUserId(String userId);

    // here we are using the @Query annotation to define a custom query to find all the products that match a given name.
    @Query("{'name': {$regex: ?0}}")
    List<Product> findByName(String name);

    // here we are using the @Query annotation to define a custom query to delete all the products that belong to a user.
    @Query("{'userId': ?0}")
    void deleteByUserId(String userId);

}
