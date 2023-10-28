package mijan.letsplay.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import mijan.letsplay.models.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    @Query("{'userId': ?0}")
    List<Product> findByUserId(String userId);

    @Query("{'name': {$regex: ?0}}")
    List<Product> findByName(String name);

    @Query("{'userId': ?0}")
    void deleteByUserId(String userId);

}
