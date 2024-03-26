package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    User findByUsername(String username);

    User findUserById(long id);

    List<User> getAllUsers();

    void saveUser(User user);

    void updateUser(User user);

    void deleteUserById(long id);

    void addUsers();

    PasswordEncoder passwordEncoder();

    StringBuilder createStringBuilder(BindingResult bindingResult);
}
