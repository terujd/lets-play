package mijan.letsplay.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;

// import lombok.launch.PatchFixesHider.Val;
import mijan.letsplay.repositories.ProductRepository;
import mijan.letsplay.controller.ProductController;
import mijan.letsplay.models.Product;
import mijan.letsplay.exceptions.NotFoundException;
import mijan.letsplay.exceptions.ProductCollectionException;
import mijan.letsplay.config.ValidateProduct;
import mijan.letsplay.models.User;
import mijan.letsplay.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public void createProduct(Product product) throws ConstraintViolationException, ProductCollectionException {
        ValidateProduct.validateProduct(product);
        if (product.getId() != null) {
            // product.setId(Product.uuidGenerator());
            // Fix this
        }
        Optional<User> userOptional = userRepository.findById(product.getUserId().trim());

        if (userOptional.isEmpty()) {
            throw new ProductCollectionException(ProductCollectionException.UserNotFoundException());
        } else {
            productRepository.save(product);
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public void updateProduct(String id, Product product) throws ProductCollectionException {
        Optional<Product> productOptional = productRepository.findById(id);

        ValidateProduct.validateProduct(product);

        Optional<User> userOptional = userRepository.findById(product.getUserId().trim());

        if (productOptional.isPresent()) {
            if (productOptional.get().getName().equals(product.getName()) &&
                    productOptional.get().getDescription().equals(product.getDescription()) &&
                    // Fix this
                    // productOptional.get().getPrice().equals(product.getPrice()) &&
                    productOptional.get().getUserId().equals(product.getUserId())) {
                throw new ProductCollectionException(ProductCollectionException.NoChangesMadeException());
            } else if (userOptional.isEmpty()) {
                throw new ProductCollectionException(ProductCollectionException.UserNotFoundException());
            }
            Product productUpdate = productOptional.get();
            productUpdate.setName(product.getName());
            productUpdate.setDescription(product.getDescription());
            productUpdate.setPrice(product.getPrice());
            productUpdate.setUserId(product.getUserId());
            productRepository.save(productUpdate);
        } else {
            throw new ProductCollectionException(ProductCollectionException.NotFoundException(id));
        }
    }

    public void deleteProduct(String id) throws ProductCollectionException {
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent()) {
            throw new ProductCollectionException(ProductCollectionException.NotFoundException(id));
        } else {
            productRepository.deleteById(id);
        }
    }
}
