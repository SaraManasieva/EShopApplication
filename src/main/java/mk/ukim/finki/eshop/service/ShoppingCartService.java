package mk.ukim.finki.eshop.service;

import mk.ukim.finki.eshop.model.Product;
import mk.ukim.finki.eshop.model.ShoppingCart;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ShoppingCartService {

    List<Product> listAllProductsInShoppingCart(Long cartId);

    ShoppingCart getActiveShoppingCart(String username);

    ShoppingCart addProductToShoppingCart(String username, Long productId);

    List<ShoppingCart> filterByDateTimeBetween(LocalDateTime from, LocalDateTime to);

    List<ShoppingCart> findAll();
    Long countSuccessfulOrders(String username);
    ShoppingCart save(ShoppingCart shoppingCart);

    Optional<ShoppingCart> findById(Long id);

}
