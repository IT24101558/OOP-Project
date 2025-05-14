package com.example.OOP_FitConnect.service;

import java.util.List;
import com.example.OOP_FitConnect.model.User;
import com.example.OOP_FitConnect.repository.DBController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class UserService {
    private final DBController dbController;

    @Autowired
    public UserService(DBController dbController) {
        this.dbController = dbController;
    }

    public User getUserById(String id) {
        return dbController.getUserById(id);
    }
    public List<User> getAllUsers() {
        return dbController.getAllUsers();
    }

    public User registerUser(User user) {
        return dbController.saveUser(user);
    }

    public User updateUser(User user) {
        return dbController.updateUser(user);
    }

    public void deleteUser(String id) {
        dbController.deleteUser(id);
    }
    // Add more user-related methods as needed
}
