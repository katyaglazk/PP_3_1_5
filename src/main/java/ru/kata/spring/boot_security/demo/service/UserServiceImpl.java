package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.kata.spring.boot_security.demo.repositories.UsersRepository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

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
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    @Override
    @Transactional
    public void update(User updatedUser, Long id) {
        User userFromDB = usersRepository.getById(id);
        if ((userFromDB != null) && !(updatedUser.getId() == userFromDB.getId())) {
            return;
        }
        if (userFromDB.getPassword() == updatedUser.getPassword()) {
            updatedUser.setPassword(userFromDB.getPassword());
        } else {
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        usersRepository.save(updatedUser);
    }

    @Override
    @Transactional
    public void delete(User user) {
        usersRepository.delete(user);
    }

    @Override
    public Optional<User> getById(Long id) {
        return usersRepository.findById(id);
    }


}
