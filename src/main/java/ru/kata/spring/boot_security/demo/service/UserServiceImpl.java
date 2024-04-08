package ru.kata.spring.boot_security.demo.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{
//public class UserServiceImpl {
    private final UsersRepository usersRepository;
    private final RoleService roleService;

    public UserServiceImpl(UsersRepository userDAO, RoleService roleService) {
        this.usersRepository = userDAO;
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public List<User> getAllUser() {
        return usersRepository.findAll();
    }

    @Override
    public User getUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public void add(User user) {
        usersRepository.save(user);
    }

    @Override
    @Transactional
    public void update(User user) {
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
