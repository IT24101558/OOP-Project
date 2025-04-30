package com.example.OOP_FitConnect;

import com.example.OOP_FitConnect.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private Map<String, User> users = new HashMap<>();

    public UserService() {
        // Initialize with demo user
        users.put("demo@example.com", new User("demo@example.com", "password", "Demo User"));
    }

    public User findByEmail(String email) {
        return users.get(email);
    }

    public boolean authenticate(String email, String password) {
        User user = findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }

    public User register(User user) {
        users.put(user.getEmail(), user);
        return user;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}
