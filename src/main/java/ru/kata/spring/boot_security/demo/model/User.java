package ru.kata.spring.boot_security.demo.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @Size(min = 2, max = 100, message = "Допустимая длина имени от 2 до 100 символов")
    private String name;

    @Size(min = 2, max = 100, message = "Допустимая длина фамилии от 2 до 100 символов")
    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Никнейм не должен быть пустым")
    @Column(name = "username")
    @Size(min=2, message = "Не меньше 5 знаков")
    private String username;
    @Column(name = "password")
    @Size(min=2, message = "Не меньше 5 знаков")
    private String password;

//    @ManyToMany(fetch = FetchType.LAZY)
//    private List<Role> roles;

    public User() {}

    public User(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public User(String name, String username, String password, String email, String surname) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.surname = surname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }
//    public void setId(Long id) {
//        this.id = id;
//    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
//    public List<Role> getRoles() {
//        return roles;
//    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return getRoles();
//    }
//
//    @Override
    public String getPassword() {
        return password;
    }
//
//    @Override
    public String getUsername() {
        return username;
    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
}
