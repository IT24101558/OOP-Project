package com.example.OOP_FitConnect.service;

import com.example.OOP_FitConnect.model.User;
import com.example.OOP_FitConnect.repository.DBController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private DBController dbController;
    @Autowired
    private GuestService guestService;

    public Map<String, Object> getDashboardStats() {
        List<User> users = guestService.getAllUsers();
        Map<String, Object> stats = new HashMap<>();

        long totalUsers = users.size();
        long verifiedUsers = users.stream().filter(User::isVerified).count();
        long activeWorkouts = users.stream()
                .flatMap(u -> u.getWorkoutPlans().stream())
                .filter(w -> !w.isCompleted())
                .count();
        long completedWorkouts = users.stream()
                .flatMap(u -> u.getWorkoutPlans().stream())
                .filter(w -> w.isCompleted())
                .count();

        Map<String, Object> userStats = new HashMap<>();
        userStats.put("total", totalUsers);
        userStats.put("verified", verifiedUsers);

        Map<String, Object> workoutStats = new HashMap<>();
        workoutStats.put("active", activeWorkouts);
        workoutStats.put("completed", completedWorkouts);

        stats.put("userStats", userStats);
        stats.put("workoutStats", workoutStats);

        return stats;
    }

    public User createAdmin(String name, String email, String password) {
        User admin = new User();
        admin.setName(name);
        admin.setEmail(email);
        admin.setPassword(password);
        admin.setRole("ADMIN");
        admin.setVerified(true);
        return dbController.saveUser(admin);
    }

    public User updateUser(String userId, String name, String email, String role) {
        User user = guestService.getUserById(userId);
        if (user != null) {
            user.setName(name);
            user.setEmail(email);
            user.setRole(role);
            return dbController.updateUser(user);
        }
        return null;
    }

    public List<User> getUsersByRole(String role) {
        return dbController.getAllUsers().stream()
                .filter(user -> user.getRole().equals(role))
                .toList();
    }

    public Map<String, Long> getUserStatistics() {
        List<User> users = guestService.getAllUsers();
        Map<String, Long> stats = new HashMap<>();

        stats.put("totalUsers", (long) users.size());
        stats.put("verifiedUsers", users.stream().filter(User::isVerified).count());
        stats.put("adminUsers", users.stream().filter(User::isAdmin).count());
        stats.put("regularUsers", users.stream().filter(u -> !u.isAdmin()).count());

        return stats;
    }
}
