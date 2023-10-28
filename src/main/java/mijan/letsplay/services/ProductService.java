package mijan.letsplay.services;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolationException;
import mijan.letsplay.config.ValidateProduct;
import mijan.letsplay.exceptions.ProductCollectionException;
import mijan.letsplay.models.Product;
import mijan.letsplay.models.User;
// import lombok.launch.PatchFixesHider.Val;
import mijan.letsplay.repositories.ProductRepository;
import mijan.letsplay.repositories.UserRepository;


// ProductService is a service class that will be used to perform CRUD operations on the Product collection.
// The ProductService class will be annotated with @Service to indicate that it is a service class.

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // The ProductService class will have a constructor that will be used to inject the ProductRepository and UserRepository.

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }


    // The createProduct method will be used to create a new product.
    // The method will take a Product object as a parameter and will return void.
    // The method will first validate the Product object using the ValidateProduct class.
    // If the Product object is valid, the method will check if the user exists in the database.
    // If the user exists, the product will be saved to the database.
    // If the user does not exist, a ProductCollectionException will be thrown.
    public void createProduct(Product product) throws ConstraintViolationException, ProductCollectionException {
        ValidateProduct.validateProduct(product);
        if (product.getId() != null) {
            product.setId(Product.uuidGenerator());
        }
        Optional<User> userOptional = userRepository.findById(product.getUserId().trim());

        if (userOptional.isEmpty()) {
            throw new ProductCollectionException(ProductCollectionException.UserNotFoundException());
        } else {
            productRepository.save(product);
        }
    }
    
    // The getAllProducts method will be used to get all the products from the database.
    // The method will return a List of Product objects.
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // The getProductById method will be used to get a product by its ID.
    // The method will take a String as a parameter and will return a Product object.
    // The method will first check if the product exists in the database.
    // If the product exists, the method will return the product.
    // If the product does not exist, the method will return null.
    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    // The updateProduct method will be used to update a product.
    public void updateProduct(String id, Product product) throws ProductCollectionException {
        // The method will first validate the Product object using the ValidateProduct class.
        Optional<Product> productOptional = productRepository.findById(id);

        // If the Product object is valid, the method will check if the product exists in the database.
        ValidateProduct.validateProduct(product);

        // If the product exists, the method will check if the product has been updated.
        Optional<User> userOptional = userRepository.findById(product.getUserId().trim());

        // If the product has been updated, the method will check if the user exists in the database.
        if (productOptional.isPresent()) {

            // If the user exists, the product will be updated in the database.
            if (productOptional.get().getName().equals(product.getName()) &&
                    productOptional.get().getDescription().equals(product.getDescription()) &&
                    productOptional.get().getUserId().equals(product.getUserId())) {

                        // If the product has not been updated, a ProductCollectionException will be thrown.
                throw new ProductCollectionException(ProductCollectionException.NoChangesMadeException());

                // If the user does not exist, a ProductCollectionException will be thrown.
            } else if (userOptional.isEmpty()) {
                throw new ProductCollectionException(ProductCollectionException.UserNotFoundException());
            }
            // If the product does not exist, a ProductCollectionException will be thrown.
            Product productUpdate = productOptional.get();// Get the product from the database
            productUpdate.setName(product.getName());// Update the name field
            productUpdate.setDescription(product.getDescription());// Update the description field
            productUpdate.setPrice(product.getPrice());// Update the price field
            productUpdate.setUserId(product.getUserId());// Update the userId field
            productRepository.save(productUpdate);// Save the updated product to the database
        } else {
            throw new ProductCollectionException(ProductCollectionException.NotFoundException(id));
        }
    }


    // The deleteProduct method will be used to delete a product.
    public void deleteProduct(String id) throws ProductCollectionException {

        // The method will first check if the product exists in the database.
        Optional<Product> productOptional = productRepository.findById(id);

        // If the product exists, the product will be deleted from the database.
        if (!productOptional.isPresent()) {

            // If the product does not exist, a ProductCollectionException will be thrown.
            throw new ProductCollectionException(ProductCollectionException.NotFoundException(id));
        } else {

            // If the product exists, the product will be deleted from the database.
            productRepository.deleteById(id);
        }
    }
}

