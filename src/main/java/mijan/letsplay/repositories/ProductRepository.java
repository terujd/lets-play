package mijan.letsplay.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import mijan.letsplay.models.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByUserId(String userId);
}
