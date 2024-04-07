package ru.kata.spring.boot_security.demo.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

//@Repository
public class UserDAOImp  {

//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Override
//    public void add(User user) {
//        entityManager.persist(user);
//    }
//
//    @Override
//    public void update(User user) {
//        entityManager.merge(user);
//    }
//
//    @Override
//    public void delete(User user){
//        entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
//    }
//
//    @Override
//    public List<User> getAllUser() {
//        return entityManager.createQuery("select u from User u left join fetch u.roles", User.class).getResultList();
//    }
//
//    @Override
//    public User getOne(Long id) {
//        return entityManager.find(User.class, id);
//    }


}
