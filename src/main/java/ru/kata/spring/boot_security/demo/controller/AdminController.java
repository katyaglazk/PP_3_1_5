package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Validated
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;


    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String admin(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        List<User> users = userService.getAllUser();
        model.addAttribute("users", users);
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "users";
    }

    @GetMapping("/new")
    public String addPage(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("newUser", new User());
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("allRoles", roles);
        return "new";
    }

    @PostMapping
    public String addUser(@ModelAttribute("newUser") @Valid User newUser, BindingResult result,
                          @RequestParam(value = "roles") List<Long> selectResult) {

        if (result.hasErrors()) {
            return "new";
        }
        userService.saveUser(newUser, roleService.getSetOfRoles(selectResult));
        if (newUser.getId() == null) {
            return "new";
        }
        return "redirect:/admin";
    }

    @PutMapping("/update/")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "rolesSelector") List<Long> selectResult) {

        userService.update(user, roleService.getSetOfRoles(selectResult));

        return "redirect:/admin";
    }

    @GetMapping("/open/")
    public String openUser(@RequestParam(value = "id", defaultValue = "") Long id, Model model) {

        User user = userService.getById(id);
        model.addAttribute("user", user);

        return "/user";
    }

    @DeleteMapping("/delete/")
    public String deleteNewUser(@RequestParam(value = "id", defaultValue = "") Long id) {

        User user = userService.getById(id);
        userService.delete(user);

        return "redirect:/admin";
    }

    @GetMapping("/user/")
    public String user(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "/user";
    }

}

