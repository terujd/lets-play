package mijan.letsplay.controller;

import mijan.letsplay.exceptions.ProductCollectionException;
import mijan.letsplay.models.Product;
import mijan.letsplay.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.ConstraintViolationException;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Create a new product
    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        try {
            productService.createProduct(product);
            return new ResponseEntity<Product>(product, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            System.out.println("ConstraintViolationException " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (ProductCollectionException e) {
            System.out.println("ProductCollectionException " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") String id, @RequestBody Product product) {
        try {
            productService.updateProduct(id, product);
            return new ResponseEntity<>("Update Product with id " + id, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Delete a product
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") String id) {
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>("Successfully deleted product with id " + id, HttpStatus.OK);
        } catch (ProductCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
// Product modellen som är hur en product ska se ut
// ProductService som innehåller funktioner för att lägga till en sak i
// databasen
// productController som tar emot ett request via tex @getmapping och då kör tex
// getproducts som finns i productservice
// @getmapping och då kör tex getproducts som finns i productservice
// product model > productservice > productcontroller
// productRepository glömde jag
// det är till för att kommunicera med databasen