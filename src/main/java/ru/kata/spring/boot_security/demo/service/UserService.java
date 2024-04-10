package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> getAllUser();
    User getUserByUsername(String username);
    void saveUser(User user, List<String> roles);
    void update(User user, List<String> roles);
    void delete(User user);
    User getById(Long id);
    Set<Role> getSetOfRoles(List<String> roleId);
}
