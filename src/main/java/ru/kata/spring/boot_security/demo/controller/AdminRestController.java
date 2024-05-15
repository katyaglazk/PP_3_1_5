package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getById(id).get(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> newUser(@RequestBody @Valid User newUser) {
        userService.saveUser(newUser);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping(value = "/edit/{id}")
    public ResponseEntity<HttpStatus> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        userService.update(updatedUser, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<HttpStatus> deleteNewUser(@PathVariable Long id) {
        User user = userService.getById(id).get();
        userService.delete(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }

}
