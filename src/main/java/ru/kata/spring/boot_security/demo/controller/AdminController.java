package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult result,
                          @RequestParam("authorities") List<String> values) {

        if (result.hasErrors()) {
            return "new";
        }
        userService.saveUser(user, values);
        if (user.getId() == null) {
            return "new";
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
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult result,
                             @RequestParam("authorities") List<String> values) {
        if (result.hasErrors()) {
            return "userUpdate";
        }
        userService.update(user, values);

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

