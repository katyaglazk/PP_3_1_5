package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;
import ru.kata.spring.boot_security.demo.model.User;


import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {
    private final UsersRepository usersRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UsersRepository userDAO, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.usersRepository = userDAO;
        this.roleService = roleService;
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
    public void saveUser(User user, List<String> valueRoles) {
        if (usersRepository.findByUsername(user.getUsername()) != null) {
            return;
        }
        Set<Role> roles = getSetOfRoles(valueRoles);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        usersRepository.save(user);
    }

    @Override
    @Transactional
    public void update(User user, List<String> valueRoles) {
        User userFromDB = usersRepository.findByUsername(user.getUsername());
        if ((userFromDB != null) && !(user.getId() == userFromDB.getId())) {
            return;
        }
        Set<Role> roles = getSetOfRoles(valueRoles);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(User user) {
        usersRepository.delete(user);
    }

    @Override
    @Transactional
    public User getById(Long id) {
        return usersRepository.findById(id).get();
    }

    @Override
    @Transactional
    public Set<Role> getSetOfRoles(List<String> roleId) {
        Set<Role> roles = new HashSet<>();
        for (String role : roleId) {
            //roles.add(roleService.getRoleById(Long.valueOf(role)));
            roles.add(roleService.getRoleById(Long.parseLong(role)));
        }
        return roles;
    }

}
