package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUser();

    void add(User user);

    void update(User user);
    void delete(User user);
    User getById(Long id);
}
