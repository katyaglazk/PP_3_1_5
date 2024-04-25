package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UsersRepository userDAO, PasswordEncoder passwordEncoder) {
        this.usersRepository = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUser() {
        return usersRepository.findAll();
    }

    @Override
    public User getUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public void saveUser(User user, Set<Role> roles) {
        if (usersRepository.findByUsername(user.getUsername()) != null) {
            return;
        }
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        usersRepository.save(user);
    }

    @Override
    @Transactional
    public void update(User user,  Set<Role> roles) {
        User userFromDB = usersRepository.findByUsername(user.getUsername());
        if ((userFromDB != null) && !(user.getId() == userFromDB.getId())) {
            return;
        }
        user.setRoles(roles);
        if (userFromDB.getPassword() == user.getPassword()) {
            user.setPassword(userFromDB.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        usersRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(User user) {
        usersRepository.delete(user);
    }

    @Override
    public User getById(Long id) {
        return usersRepository.findById(id).get();
    }


}
