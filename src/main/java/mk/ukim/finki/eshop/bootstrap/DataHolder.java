package mk.ukim.finki.eshop.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.eshop.embeddables.UserAddress;
import mk.ukim.finki.eshop.model.*;
import mk.ukim.finki.eshop.model.enumerations.ShoppingCartStatus;
import mk.ukim.finki.eshop.repository.jpa.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import mk.ukim.finki.eshop.model.Role;

import java.util.ArrayList;
import java.util.List;
@Component
public class DataHolder {
    public static List<User> users = null;
    public static List<Category> categories = null;
    public static List<Manufacturer> manufacturers = null;
    public static List<Product> products = null;
    public static List<ShoppingCart> shoppingCarts = null;


    private final CategoryRepository categoryRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final ProductRepository productRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataHolder(CategoryRepository categoryRepository, ManufacturerRepository manufacturerRepository, ProductRepository productRepository, ShoppingCartRepository shoppingCartRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
            this.categoryRepository = categoryRepository;
            this.manufacturerRepository = manufacturerRepository;
            this.productRepository = productRepository;
            this.shoppingCartRepository = shoppingCartRepository;
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
        }

        @PostConstruct
        public void init() {
            users = new ArrayList<>();
            categories = new ArrayList<>();
            manufacturers = new ArrayList<>();
            products = new ArrayList<>();
            shoppingCarts = new ArrayList<>();



            if (userRepository.count() == 0) {
            users.add(
                    new User(
                            "alice.smith",
                            passwordEncoder.encode("as"),
                            "Alice",
                            "Smith",
                            new UserAddress("France", "Paris", "123 Rue de Rivoli, 75001", "456 Avenue des Champs-Élysées, 75008"),
                            Role.ROLE_USER
                    ));
            users.add(
                    new User(
                            "jane.doe",
                            passwordEncoder.encode("jd"),
                            "Jane",
                            "Doe",
                            new UserAddress("Canada", "Toronto", "100 King St W", "300 Front St W"),
                            Role.ROLE_USER
                    ));
            users.add(
                    new User(
                            "emily.davis",
                            passwordEncoder.encode("ed"),
                            "Emily",
                            "Davis",
                            new UserAddress("Australia", "Sydney", "1 Martin Place", "100 George St, The Rocks"),
                            Role.ROLE_USER
                    ));
            users.add(
                    new User(
                            "sara.manasieva",
                            passwordEncoder.encode("sm"),
                            "Sara",
                            "Manasieva",
                            new UserAddress("Macedonia", "Skopje", "Ul. Skopska", "2/3-1"),
                            Role.ROLE_USER
                    ));
            users.add(
                    new User(
                            "admin",
                            passwordEncoder.encode("admin"),
                            "admin",
                            "admin",
                            new UserAddress(),
                            Role.ROLE_ADMIN
                    )
            );
            userRepository.saveAll(users);
        }

        if (categoryRepository.count() == 0) {
            categories.add(new Category("Books", "Books category"));
            categories.add(new Category("Sports", "Sports category"));
            categories.add(new Category("Food", "Food category"));
            categories.add(new Category("Clothing", "Clothing category"));
            categories.add(new Category("Musical Instruments", "Musical Instruments category"));
            categoryRepository.saveAll(categories);
        }

        if (manufacturerRepository.count() == 0) {
            manufacturers.add(new Manufacturer("Nike", "USA"));
            manufacturers.add(new Manufacturer("Colavita", "IT"));
            manufacturers.add(new Manufacturer("Literatura", "MK"));
            manufacturers.add(new Manufacturer("Yamaha","JP"));
            manufacturers.add(new Manufacturer("Zara","ES"));
            manufacturerRepository.saveAll(manufacturers);
        }

        if (productRepository.count() == 0) {
            List<Category> categoryList = categoryRepository.findAll();
            List<Manufacturer> manufacturerList = manufacturerRepository.findAll();

            products.add(new Product("Basketball", 60d, 1000, categoryList.get(1), manufacturerList.get(0)));
            products.add(new Product("Hoodie", 40.99, 200, categoryList.get(3), manufacturerList.get(4)));
            products.add(new Product("Gloves", 20d, 56000, categoryList.get(3), manufacturerList.get(4)));
            products.add(new Product("Guitar", 1090.99, 20, categoryList.get(4), manufacturerList.get(3)));
            products.add(new Product("Piano", 6590.99, 20, categoryList.get(4), manufacturerList.get(3)));
            products.add(new Product("Harry Potter and the Philosopher's Stone", 20.99, 20, categoryList.get(0), manufacturerList.get(2)));
            products.add(new Product("Spaghetti", 5.99, 20, categoryList.get(2), manufacturerList.get(1)));
            productRepository.saveAll(products);
        }

        if (shoppingCartRepository.count() == 0) {
            List<Product> productList = productRepository.findAll();
            List<User> userList = userRepository.findAll();

            ShoppingCart shoppingCart1 = new ShoppingCart(userList.get(0));
            ShoppingCart shoppingCart2 = new ShoppingCart(userList.get(1));
            ShoppingCart shoppingCart3 = new ShoppingCart(userList.get(1));

            shoppingCart1.getProducts().add(productList.get(0));

            shoppingCart2.getProducts().add(productList.get(0));
            shoppingCart2.getProducts().add(productList.get(1));
            shoppingCart2.getProducts().add(productList.get(2));

            shoppingCart3.getProducts().add(productList.get(0));

            shoppingCart1.setStatus(ShoppingCartStatus.FINISHED);
            shoppingCart2.setStatus(ShoppingCartStatus.FINISHED);
            shoppingCart3.setStatus(ShoppingCartStatus.FINISHED);

            shoppingCartRepository.save(shoppingCart1);
            shoppingCartRepository.save(shoppingCart2);
            shoppingCartRepository.save(shoppingCart3);
        }
    }
}
