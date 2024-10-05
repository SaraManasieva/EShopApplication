package mk.ukim.finki.eshop.service.impl;

import mk.ukim.finki.eshop.model.Product;
import mk.ukim.finki.eshop.model.ShoppingCart;
import mk.ukim.finki.eshop.model.User;
import mk.ukim.finki.eshop.model.enumerations.ShoppingCartStatus;
import mk.ukim.finki.eshop.model.exceptions.ProductAlreadyInShoppingCartException;
import mk.ukim.finki.eshop.model.exceptions.ProductNotFoundException;
import mk.ukim.finki.eshop.model.exceptions.ShoppingCartNotFoundException;
import mk.ukim.finki.eshop.model.exceptions.UserNotFoundException;
import mk.ukim.finki.eshop.repository.inmemory.InMemoryShoppingCartRepository;
import mk.ukim.finki.eshop.repository.inmemory.InMemoryUserRepository;
import mk.ukim.finki.eshop.repository.jpa.ShoppingCartRepository;
import mk.ukim.finki.eshop.repository.jpa.UserRepository;
import mk.ukim.finki.eshop.service.ProductService;
import mk.ukim.finki.eshop.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ProductService productService;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, UserRepository userRepository, ProductService productService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.productService = productService;
    }

    @Override
    public List<Product> listAllProductsInShoppingCart(Long cartId) {
        if (!this.shoppingCartRepository.findById(cartId).isPresent())
            throw new ShoppingCartNotFoundException(cartId);
        return this.shoppingCartRepository.findById(cartId).get().getProducts();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        return this.shoppingCartRepository
                .findByUserUsernameAndStatus(username, ShoppingCartStatus.CREATED)
                .orElseGet(() -> {
                    User user = this.userRepository.findByUsername(username)
                            .orElseThrow(() -> new UserNotFoundException(username));
                    ShoppingCart shoppingCart = new ShoppingCart(user);
                    return this.shoppingCartRepository.save(shoppingCart);
                });
    }

    @Override
    public ShoppingCart addProductToShoppingCart(String username, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        Product product = this.productService.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        if (shoppingCart.getProducts()
                .stream().filter(i -> i.getId().equals(productId))
                .collect(Collectors.toList()).size() > 0)
            throw new ProductAlreadyInShoppingCartException(productId, username);
        shoppingCart.getProducts().add(product);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public List<ShoppingCart> filterByDateTimeBetween(LocalDateTime from, LocalDateTime to) {
        return this.shoppingCartRepository.findByDateCreatedBetween(from, to);
    }

    @Override
    public List<ShoppingCart> findAll() {
        return this.shoppingCartRepository.findAll();
    }
    @Override
    public Long countSuccessfulOrders(String username) {
        return this.shoppingCartRepository.countSuccessfulOrdersByUsername(username);
    }

    @Override
    public ShoppingCart save(ShoppingCart cart) {
        return this.shoppingCartRepository.save(cart);
    }

    @Override
    public Optional<ShoppingCart> findById(Long id) {
        return this.shoppingCartRepository.findById(id);
    }


}

