package mk.ukim.finki.eshop.service.impl;

import mk.ukim.finki.eshop.embeddables.UserAddress;
import mk.ukim.finki.eshop.model.User;
import mk.ukim.finki.eshop.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.eshop.model.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.eshop.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.eshop.repository.inmemory.InMemoryUserRepository;
import mk.ukim.finki.eshop.repository.jpa.UserRepository;
import mk.ukim.finki.eshop.service.AuthService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User login(String username, String password) {
            if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
                throw new InvalidArgumentsException();
            }

            return userRepository.findByUsernameAndPassword(username, password)
                    .orElseThrow(InvalidUserCredentialsException::new);
        }

        @Override
        public List<User> findAll() {
            return userRepository.findAll();
        }
    }
