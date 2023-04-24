package org.example.repository;


import java.util.List;

public interface UserRepository {
    User save(User user);
    List<User> findAll();
    User findById(int id);
    User findByUsername(String username);
    void delete(User user);
}