package mijan.letsplay.controller;

import mijan.letsplay.models.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Create a new product
    @PostMapping("/create")
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    // Get all products (accessible by all users)
    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Get all products created by a specific user
    @GetMapping("/user/{userId}")
    public List<Product> getAllProductsByUser(@PathVariable String userId) {
        return productService.getAllProductsByUser(userId);
    }

    // Get a specific product by id
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    // Update a product
    @PutMapping("/update")
    public Product updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product.getId(), product);
    }

    // Delete a product
    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }
}
