package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUser();

    User getUserByUsername(String username);

    void saveUser(User user);

    void update(User user, Long id);

    void delete(User user);

    Optional<User> getById(Long id);

}
