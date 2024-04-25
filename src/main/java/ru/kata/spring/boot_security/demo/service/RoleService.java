package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import java.util.List;
import java.util.Set;

public interface RoleService {

    Role getRoleById(Long id);

    List<Role> getAllRoles();

    Set<Role> getSetOfRoles(List<Long> roleId);
}
