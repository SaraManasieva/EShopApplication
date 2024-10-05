package mk.ukim.finki.eshop.service.impl;

import mk.ukim.finki.eshop.embeddables.UserAddress;
import mk.ukim.finki.eshop.model.Role;
import mk.ukim.finki.eshop.model.User;
import mk.ukim.finki.eshop.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.eshop.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.eshop.model.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.eshop.repository.jpa.UserRepository;
import mk.ukim.finki.eshop.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname, Role role) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }

        if (!password.equals(repeatPassword)) {
            throw new PasswordsDoNotMatchException();
        }

        if(this.userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException(username);
        }

        User user = new User(
                username,
                passwordEncoder.encode(password),
                name,
                surname,
                new UserAddress(),
                role
        );

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
    }
}
