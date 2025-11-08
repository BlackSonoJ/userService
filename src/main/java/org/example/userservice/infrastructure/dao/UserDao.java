package org.example.userservice.infrastructure.dao;


import org.example.userservice.domain.entities.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();
    User findById(Long id);
    User save(User user);
    User update(Long id, User user);
    void delete(Long id);
}
