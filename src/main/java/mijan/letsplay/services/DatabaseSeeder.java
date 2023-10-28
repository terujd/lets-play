package mijan.letsplay.services;



import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import mijan.letsplay.models.Product;
import mijan.letsplay.models.User;
import mijan.letsplay.repositories.ProductRepository;
import mijan.letsplay.repositories.UserRepository;



// DabaseSeeder is a class that will be run when the application starts. It will check if there are any users in the database, and if not, it will seed the database with some initial users.
// The DatabaseSeeder class will be annotated with @Component to indicate that it is a component class.
@Component
public class DatabaseSeeder implements CommandLineRunner {
    // The DatabaseSeeder class will have a constructor that will be used to inject the UserRepository and ProductRepository.
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    // The DatabaseSeeder class will implement the CommandLineRunner interface and override the run method.
    // The run method will check if there are any users in the database, and if not, it will seed the database with some initial users.
    public DatabaseSeeder(UserRepository userRepository, ProductRepository productRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // The run method will check if there are any users in the database, and if not, it will seed the database with some initial users.
    // The run method will also check if there are any products in the database, and if not, it will seed the database with some initial products.
    // The run method will be annotated with @Override to indicate that it is overriding the run method from the CommandLineRunner interface.
    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {//if there are no users in the database
            seedUsers();
        }
        if (productRepository.count() == 0) {//if there are no products in the database
            seedProducts();
        }
    }


    // The seedUsers method will be used to seed the database with some initial users.
    // The method will create two users and save them to the database.
    private void seedUsers() {
        // Seed users with default password of "password" and roles of "ROLE_USER" and "ROLE_ADMIN" respectively

        var user1 = User.builder()
                .id("1")//id is set to 1
                .name("user")//name is set to user
                .email("user@example.com")//email is set to user@example
                .password(passwordEncoder.encode("password"))//password is set to password
                .role("ROLE_USER")//role is set to ROLE_USER
                .build();//build the user

        var user2 = User.builder()//create another user
                .id("2")//id is set to 2
                .name("admin")//name is set to admin
                .email("admin@example.com")//email is set to admin@example
                .password(passwordEncoder.encode("password"))//password is set to password
                .role("ROLE_ADMIN")//role is set to ROLE_ADMIN
                .build();//build the user

        userRepository.save(user1);//save user1
        userRepository.save(user2);//save user2

        System.out.println("Initial users seeded.");//print this message
    }

    // The seedProducts method will be used to seed the database with some initial products.
    public void seedProducts() {
        Product product1 = new Product();//create a new product
        product1.setName("Product 1");//set the name of the product to Product 1
        product1.setDescription("This is product 1");//set the description of the product to This is product 1
        product1.setPrice(Double.parseDouble("100.0"));//set the price of the product to 100.0
        product1.setUserId("1");//set the user ID of the product to 1
        productRepository.save(product1);//save the product
        System.out.println("Product 1 added");//print this message
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