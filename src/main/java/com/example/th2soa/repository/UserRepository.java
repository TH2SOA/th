package com.example.th2soa.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.th2soa.entity.User;

@Repository
public class UserRepository {

    private final List<User> users = new ArrayList<>();

    public UserRepository() {
        // Tai khoan demo: admin / 123456 (Base64: MTIzNDU2)
        users.add(new User(1, "admin", "MTIzNDU2"));
    }

    public Optional<User> findByUserName(String userName) {
        return users.stream()
                .filter(u -> u.getUserName().equals(userName))
                .findFirst();
    }

    public User save(User user) {
        return user;
    }
}