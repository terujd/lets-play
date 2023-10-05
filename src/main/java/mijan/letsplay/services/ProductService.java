package mijan.letsplay.services;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import mijan.letsplay.repositories.ProductRepository;
import mijan.letsplay.controller.ProductController;
import mijan.letsplay.models.Product;
import mijan.letsplay.exceptions.NotFoundException;

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

        // Validate the product's fields.
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }

        // You can add more validations here.

        try {
            // Save the product to the database.
            Product savedProduct = productRepository.save(product);
            return savedProduct;
        } catch (Exception e) {
            // Handle any database-related exceptions here.
            throw new RuntimeException("Failed to create new product: " + e.getMessage(), e);
        }
    }

    public List<Product> getAllProducts() {
        // Implement logic to retrieve all products.
        List<Product> products = productRepository.findAll();
        return products;
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
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setPrice(updatedProduct.getPrice());
            // You can update other fields here as well.
            return productRepository.save(existingProduct);
        } else {
            return null; // Product not found.
        }
    }

    public void deleteProduct(String id) {
        // Implement logic to delete a product by its ID.
        try {
            productRepository.deleteById(id);
        } catch (Throwable e) {
            // Handle the case where the product is not found.
            // You can return a 404 Not Found error or a custom error message.
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found", e);
        }
    }

    public List<Product> getAllProductsByUser(String userId) {
        List<Product> productsByUser = productRepository.findByUserId(userId);

        // Handle the case where no products are found for the user.
        if (productsByUser.isEmpty()) {
            // You can throw an exception, return an empty list, or handle it in another way based on your application's needs.
            // For expample, throw a custom exception or return a 404 Not Found error.
            throw new NotFoundException("No products found for user with ID: " + userId);
        }
        return productsByUser;
    }

    public Product updateProduct(ProductController name, Product product) {
        // Perform any validation or business logic here, if needed.

        // Check if the product exists in the database by its ID.
        Optional<Product> existingProductOptional = productRepository.findById(product.getId());
        if (existingProductOptional.isPresent()) {
            // If the product exists, update its fields with the new data.
            Product existingProduct = existingProductOptional.get();

            // Update fields as needed. For example:
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());

            // Save the updated product to the database.
            Product updatedProduct = productRepository.save(existingProduct);
            return updatedProduct;
        } else {
            // if the product does not exist, you can handle this case.
            // You might throw an exception, return null, or follow a different error-handling strategy.
            // return null for now;
            return null;
        }
    }
}
