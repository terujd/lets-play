package mijan.letsplay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.ConstraintViolationException;
import mijan.letsplay.exceptions.ProductCollectionException;
import mijan.letsplay.models.Product;
import mijan.letsplay.services.ProductService;

@RestController
@RequestMapping("/api/product")//the base url for the product controller
// ProductController is a controller class that will be used to handle requests to the /api/product endpoint.
public class ProductController {

    // The ProductService bean is used to perform CRUD operations on the Product collection.
    @Autowired
    private ProductService productService;

    // Create a new product
    @PostMapping("/create")//the create product endpoint
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")//only admin and user can create a product
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        try {//try to create the product
            productService.createProduct(product);//create the product
            return new ResponseEntity<Product>(product, HttpStatus.OK);//return a success message
        } catch (ConstraintViolationException e) {//catch the exception
            System.out.println("ConstraintViolationException " + e.getMessage());//print the exception message
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);//return an unprocessable entity message
        } catch (ProductCollectionException e) {
            System.out.println("ProductCollectionException " + e.getMessage());//print the exception message
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);//return a conflict message
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);//return a bad request message
        }
    }

    // Get all products (accessible by all users)
    @GetMapping("/all")//the all products endpoint
    public List<Product> getAllProducts() {
        //the getAllProducts method is called
        return productService.getAllProducts();
    }

    // Get a specific product by id
    @GetMapping("/{id}")//the id is passed as a path variable
    public Product getProductById(@PathVariable String id) {
        //the getProductById method is called and the id is passed as a parameter
        return productService.getProductById(id);
    }

    // Update a product
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")//only admin can update a product
    // The @PreAuthorize annotation is used to specify the roles that are allowed to access the endpoint.
    public ResponseEntity<?> updateProduct(@PathVariable("id") String id, @RequestBody Product product) {
        try {
            productService.updateProduct(id, product);//update the product
            return new ResponseEntity<>("Update Product with id " + id, HttpStatus.OK);//return a success message
        } catch (ConstraintViolationException e) {//catch the exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);//return an unprocessable entity message
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);//return a not found message
        }
    }

    // Delete a product
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")//only admin can delete a product
    public ResponseEntity<?> deleteProduct(@PathVariable("id") String id) {
        //try to delete the product
        try {
            productService.deleteProduct(id);//delete the product
            return new ResponseEntity<>("Successfully deleted product with id " + id, HttpStatus.OK);//return a success message
        } catch (ProductCollectionException e) {//catch the exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);//return a not found message
        }
    }
}
