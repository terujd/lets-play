package mijan.letsplay.controller;

import mijan.letsplay.models.Product;
import mijan.letsplay.services.ProductService;

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
// Product modellen som är hur en product ska se ut
// ProductService som innehåller funktioner för att lägga till en sak i databasen
// productController som tar emot ett request via tex @getmapping och då kör tex getproducts som finns i productservice
// @getmapping och då kör tex getproducts som finns i productservice
// product model > productservice > productcontroller
// productRepository glömde jag
// det är till för att kommunicera med databasen