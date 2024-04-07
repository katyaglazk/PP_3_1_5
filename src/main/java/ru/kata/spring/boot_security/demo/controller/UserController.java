package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RegistrationService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;


@Controller
@RequestMapping(value = "")
public class UserController {

    private final UserValidator userValidator;
    private final RegistrationService registrationService;

    @Autowired
    public UserController(UserValidator userValidator, RegistrationService registrationService) {
        this.userValidator = userValidator;
        this.registrationService = registrationService;
    }

    @GetMapping("/index")
    public String index() {
        //model.addAttribute("user", new User());
        return "index";
    }

    @GetMapping("/showUserInfo")
    public String showUserInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        System.out.println(userDetails.getUsername());
        return "index";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user) {
        return "registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") @Valid User user,
                                      BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/registration";
        }
        registrationService.register(user);
        return "redirect:/user";
    }
//    private final UserService userService;
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }

//    @GetMapping("/users")
//    public String getUsers(Model model) {
//        model.addAttribute("users", userService.getAllUser());
//        return "users";
//    }
//
//    @GetMapping("/new")
//    public String addNewUser(Model model) {
//        model.addAttribute("user", new User());
//        return "new";
//    }
//
//    @PostMapping
//    public String saveUser(@ModelAttribute("user") User user) {
//        if (user.getId() == null) {
//            userService.add(user);
//        } else {
//            userService.update(user);
//        }
//        return "redirect:/users";
//    }
//
//    @GetMapping("/update/")
//    public String updateUser(@RequestParam(value = "id", defaultValue = "") Long id,
//                                            Model model) {
//        User user = userService.getById(id);
//        model.addAttribute("user", user);
//        return "new";
//    }
//
//    @GetMapping("/delete/")
//    public String deleteNewUser(@RequestParam(value = "id", defaultValue = "") Long id) {
//
//        User user = userService.getById(id);
//        userService.delete(user);
//
//        return "redirect:/users";
//    }
//
//    @GetMapping("/authenticated")
//    public String pageForAuthenticated(Model model) {
//        return "";
//    }
//
//    @GetMapping("/user")
//    public String user(Model model) {
//        return "user";
//    }
//
//    @GetMapping("/admin")
//    public String admin(Model model) {
//        return "users";
//    }

}
