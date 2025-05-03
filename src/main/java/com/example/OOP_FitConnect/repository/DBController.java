package com.example.OOP_FitConnect.repository;

import com.example.OOP_FitConnect.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class DBController {

    private final Map<String, User> usersById = new ConcurrentHashMap<>();
    private final Map<String, User> usersByEmail = new ConcurrentHashMap<>();
    private final Map<String, User> usersByVerificationToken = new ConcurrentHashMap<>();
    private final Map<String, User> usersByResetToken = new ConcurrentHashMap<>();

    public User saveUser(User user) {
        usersById.put(user.getId(), user);
        usersByEmail.put(user.getEmail(), user);

        if (user.getVerificationToken() != null) {
            usersByVerificationToken.put(user.getVerificationToken(), user);
        }

        if (user.getResetToken() != null) {
            usersByResetToken.put(user.getResetToken(), user);
        }
        return user;
    }

    public User updateUser(User user) {
        User existingUser = usersById.get(user.getId());

        if (existingUser != null) {
            if (existingUser.getVerificationToken() != null) {
                usersByVerificationToken.remove(existingUser.getVerificationToken());
            }

            if (existingUser.getResetToken() != null) {
                usersByResetToken.remove(existingUser.getResetToken());
            }
        }

        usersById.put(user.getId(), user);
        usersByEmail.put(user.getEmail(), user);

        if (user.getVerificationToken() != null) {
            usersByVerificationToken.put(user.getVerificationToken(), user);
        }

        if (user.getResetToken() != null) {
            usersByResetToken.put(user.getResetToken(), user);
        }
        return existingUser;
    }

    public User getUserById(String id) {
        return usersById.get(id);
    }

    public User getUserByEmail(String email) {
        return usersByEmail.get(email);
    }

    public User getUserByVerificationToken(String token) {
        return usersByVerificationToken.get(token);
    }

    public User getUserByResetToken(String token) {
        return usersByResetToken.get(token);
    }

    public void deleteUser(String id) {
        User user = usersById.get(id);

        if (user != null) {
            usersById.remove(id);
            usersByEmail.remove(user.getEmail());

            if (user.getVerificationToken() != null) {
                usersByVerificationToken.remove(user.getVerificationToken());
            }

            if (user.getResetToken() != null) {
                usersByResetToken.remove(user.getResetToken());
            }
        }
    }

    // New method to get all users
    public List<User> getAllUsers() {
        return new ArrayList<>(usersById.values());
    }
}