package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UsersDetailsService;

@Component
public class UserValidator implements Validator {

    private final UsersDetailsService usersDetailsService;

    @Autowired
    public UserValidator(UsersDetailsService usersDetailsService) {
        this.usersDetailsService = usersDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        try {
            usersDetailsService.loadUserByUsername(user.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return;
        }

        errors.rejectValue("username", "", "Пользователь с указанным ником уже существует");


    }
}
