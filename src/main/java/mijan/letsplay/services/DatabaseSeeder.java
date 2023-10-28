package mijan.letsplay.services;



import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import mijan.letsplay.models.Product;
import mijan.letsplay.models.User;
import mijan.letsplay.repositories.ProductRepository;
import mijan.letsplay.repositories.UserRepository;




@Component
public class DatabaseSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(UserRepository userRepository, ProductRepository productRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
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
        var user1 = User.builder()
                .id("1")
                .name("user")
                .email("user@example.com")
                .password(passwordEncoder.encode("password"))
                .role("ROLE_USER")
                .build();

        var user2 = User.builder()
                .id("2")
                .name("admin")
                .email("admin@example.com")
                .password(passwordEncoder.encode("password"))
                .role("ROLE_ADMIN")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        System.out.println("Initial users seeded.");
    }

    public void seedProducts() {
        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setDescription("This is product 1");
        product1.setPrice(Double.parseDouble("100.0"));
        product1.setUserId("1");
        productRepository.save(product1);
        System.out.println("Product 1 added");
    }
}


// import org.springframework.boot.CommandLineRunner;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Component;

// import mijan.letsplay.models.Product;
// import mijan.letsplay.models.User;
// import mijan.letsplay.repositories.ProductRepository;
// import mijan.letsplay.repositories.UserRepository;

// @Component
// public class DatabaseSeeder implements CommandLineRunner {

//     private final UserRepository userRepository;
//     private final ProductRepository productRepository;
//     private final PasswordEncoder passwordEncoder;

//     public DatabaseSeeder(UserRepository userRepository, ProductRepository productRepository,
//             PasswordEncoder passwordEncoder) {
//         this.userRepository = userRepository;
//         this.productRepository = productRepository;
//         this.passwordEncoder = passwordEncoder;
//     }

//     @Override
//     public void run(String... args) throws Exception {
//         if (userRepository.count() == 0 && productRepository.count() == 0) {
//             seedData();
//         }
//     }

//     private void seedData() {
//         // Seed users
//         User user1 = User.builder()
//                 .id("1")
//                 .name("user")
//                 .email("user@example.com")
//                 .password(passwordEncoder.encode("password"))
//                 .role("ROLE_USER")
//                 .build();

//         User user2 = User.builder()
//                 .id("2")
//                 .name("admin")
//                 .email("admin@example.com")
//                 .password(passwordEncoder.encode("password"))
//                 .role("ROLE_ADMIN")
//                 .build();

//         userRepository.save(user1);
//         userRepository.save(user2);

//         // Seed products
//         Product product1 = Product.builder()
//                 .id("1")
//                 .name("Product 1")
//                 .price(100.0)
//                 .description("This is product 1")
//                 .userId("1")
//                 .build();

//         Product product2 = Product.builder()
//                 .id("2")
//                 .name("Product 2")
//                 .price(200.0)
//                 .description("This is product 2")
//                 .userId("2")
//                 .build();

//         productRepository.save(product1);
//         productRepository.save(product2);

//         System.out.println("Initial users and products seeded.");
//     }
// }