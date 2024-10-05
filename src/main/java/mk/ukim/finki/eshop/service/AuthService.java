package mk.ukim.finki.eshop.service;

import mk.ukim.finki.eshop.model.User;

import java.util.List;

public interface AuthService {
    User login(String username, String password);

    List<User> findAll();
}
