package mijan.letsplay.controller;


import org.springframework.stereotype.Service;
import mijan.letsplay.repositories.ProductRepository;
import mijan.letsplay.models.Product;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        // Implement logic to create a new product, e.g., save it to the database.
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        // Implement logic to retrieve all products.
        return productRepository.findAll();
    }

    public Product getProductById(String id) {
        // Implement logic to retrieve a product by its ID.
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.orElse(null);
    }

    public Product updateProduct(String id, Product updatedProduct) {
        // Implement logic to update an existing product by its ID.
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product existingProduct = productOptional.get();
            // Update the existing product's fields with values from updatedProduct.
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDesctiption());
            existingProduct.setPrice(updatedProduct.getPrice());
            // You can update other fields here as well.
            return productRepository.save(existingProduct);
        } else {
            return null; // Product not found.
        }
    }

    public void deleteProduct(String id) {
        // Implement logic to delete a product by its ID.
        productRepository.deleteById(id);
    }

    public List<Product> getAllProductsByUser(String userId) {
        return null;
    }

    public Product updateProduct(ProductController name, Product product) {
        return null;
    }
}
