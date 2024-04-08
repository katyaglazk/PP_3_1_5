package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder getPasswordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, PasswordEncoder getPasswordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.getPasswordEncoder = getPasswordEncoder;
    }

    @GetMapping("")
    public String admin(Model model) {

        List<User> users = userService.getAllUser();
        model.addAttribute("users", users);

        return "users";
    }

    @GetMapping("/new")
    public String addPage(Model model) {

        model.addAttribute("user", new User());
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("allRoles", roles);

        return "new";
    }

    @PostMapping
    public String addUser(@ModelAttribute("user") @Valid User user,
                          @RequestParam("authorities") List<String> values) {

        Set<Role> roles = userService.getSetOfRoles(values);
        user.setRoles(roles);
        user.setPassword(getPasswordEncoder.encode(user.getPassword()));

        if (user.getId() == null) {
            userService.add(user);
        } else {
            userService.update(user);
        }

        return "redirect:/admin";
    }

    @GetMapping("/update/")
    public String update(@RequestParam(value = "id", defaultValue = "") Long id, Model model) {

        User user = userService.getById(id);
        model.addAttribute("user", user);
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("allRoles", roles);

        return "/userUpdate";
    }

    @PostMapping("/update/")
    public String updateUser(@RequestParam("id") Long id, User user,
                             @RequestParam("authorities") List<String> values) {

        Set<Role> roleSet = userService.getSetOfRoles(values);
        user.setRoles(roleSet);
        userService.update(user);

        return "redirect:/admin";
    }

    @GetMapping("/open/")
    public String openUser(@RequestParam(value = "id", defaultValue = "") Long id, Model model) {

        User user = userService.getById(id);
        model.addAttribute("user", user);

        return "/user";
    }

    @GetMapping("/delete/")
    public String deleteNewUser(@RequestParam(value = "id", defaultValue = "") Long id) {

        User user = userService.getById(id);
        userService.delete(user);

        return "redirect:/admin";
    }
}

