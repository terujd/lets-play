package mijan.letsplay.services;

import mijan.letsplay.models.User;
import mijan.letsplay.models.Product;
import mijan.letsplay.repositories.ProductRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import mijan.letsplay.repositories.UserRepository;



@Component
public class DatabaseSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public DatabaseSeeder(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            seedUsers();
        }
        if (productRepository.count() == 0) {
            seedProducts();
        }
    }

    private void seedUsers() {
        User user1 = new User();
        user1.setName("Mijanur Rahman");
        user1.setEmail("Mijan@gmail.com");
        user1.setPassword("123456");
        user1.setRole("ROLE_ADMIN");
        userRepository.save(user1);
        System.out.println("User 1 added");
    }

    public void seedProducts() {
        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setDescription("This is product 1");
        product1.setPrice("100");
        product1.setUserId("1");
        productRepository.save(product1);
        System.out.println("Product 1 added");
    }
}
