package ru.kata.spring.boot_security.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;

//@Entity
//@Table(name = "t_role")
public class Role implements GrantedAuthority {

//    @Id
    private Long id;
    private String name;

//    @ManyToMany(mappedBy = "roles")
//    private List<User> users;

    public Role() {
    }

    public Role(Long id) {
        this.id = id;
    }

    public Role(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

//    public List<User> getUsers() {
//        return users;
//    }

//    public void setUsers(List<User> users) {
//        this.users = users;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
