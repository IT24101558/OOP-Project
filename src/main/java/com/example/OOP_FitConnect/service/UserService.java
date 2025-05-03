package com.example.OOP_FitConnect.service;

import com.example.OOP_FitConnect.model.User;
import com.example.OOP_FitConnect.model.WorkoutPlan;
import com.example.OOP_FitConnect.repository.DBController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private DBController dbController;

    @Autowired
    private JavaMailSender mailSender;

    @PostConstruct
    public void init() {
        // Check if admin already exists
        User existingAdmin = dbController.getUserByEmail("admin@user.com");
        if (existingAdmin == null) {
            // Create predefined admin
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@user.com");
            admin.setPassword("1234567890");
            admin.setRole("ADMIN");
            admin.setVerified(true);
            dbController.saveUser(admin);
        }
    }

    public User authenticate(String email, String password) {
        User user = dbController.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User registerUser(String name, String email, String password, String verificationToken) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setVerificationToken(verificationToken);
        user.setRole("USER"); // Set default role

        dbController.saveUser(user);
        return user;
    }

    public List<User> getAllUsers() {
        return dbController.getAllUsers();
    }

    public User getUserByEmail(String email) {
        return dbController.getUserByEmail(email);
    }

    public User getUserById(String id) {
        return dbController.getUserById(id);
    }

    public boolean verifyEmail(String token) {
        User user = dbController.getUserByVerificationToken(token);
        if (user != null) {
            user.setVerified(true);
            user.setVerificationToken(null);
            dbController.updateUser(user);
            return true;
        }
        return false;
    }

    public String generatePasswordResetToken(User user) {
        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        user.setResetTokenExpiry(System.currentTimeMillis() + (24 * 60 * 60 * 1000));
        dbController.updateUser(user);
        return resetToken;
    }

    public boolean isValidResetToken(String token) {
        User user = dbController.getUserByResetToken(token);
        return user != null && user.getResetTokenExpiry() > System.currentTimeMillis();
    }

    public boolean resetPassword(String token, String newPassword) {
        User user = dbController.getUserByResetToken(token);
        if (user != null && user.getResetTokenExpiry() > System.currentTimeMillis()) {
            user.setPassword(newPassword);
            user.setResetToken(null);
            user.setResetTokenExpiry(0);
            dbController.updateUser(user);
            return true;
        }
        return false;
    }

    public void updateUserBmi(String userId, double bmi) {
        User user = dbController.getUserById(userId);
        if (user != null) {
            user.setBmi(bmi);
            dbController.updateUser(user);
        }
    }

    public void sendVerificationEmail(User user, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Verify your FitConnect account");
        message.setText("Hello " + user.getName() + ",\n\n" +
                "Please verify your email by clicking the link below:\n" +
                "http://localhost:8080/verify?token=" + token + "\n\n" +
                "Thank you,\nFitConnect Team");

        mailSender.send(message);
    }

    public void sendPasswordResetEmail(User user, String resetToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("FitConnect Password Reset");
        message.setText("Hello " + user.getName() + ",\n\n" +
                "Please reset your password by clicking the link below:\n" +
                "http://localhost:8080/reset-password?token=" + resetToken + "\n\n" +
                "This link will expire in 24 hours.\n\n" +
                "Thank you,\nFitConnect Team");

        mailSender.send(message);
    }

    public List<WorkoutPlan> getUserWorkoutPlans(String userId) {
        User user = dbController.getUserById(userId);
        return user != null ? user.getWorkoutPlans() : List.of();
    }

    public void addWorkoutPlan(String userId, WorkoutPlan workoutPlan) {
        User user = dbController.getUserById(userId);
        if (user != null) {
            user.addWorkoutPlan(workoutPlan);
            dbController.updateUser(user);
        }
    }

    public boolean canAccessWorkout(String userId, String workoutId) {
        User user = dbController.getUserById(userId);
        if (user == null) return false;

        if (user.isAdmin()) return true;

        return user.getWorkoutPlans().stream()
                .anyMatch(plan -> plan.getId().equals(workoutId));
    }

    public boolean completeWorkout(String userId, String workoutId) {
        User user = dbController.getUserById(userId);
        if (user != null) {
            for (WorkoutPlan plan : user.getWorkoutPlans()) {
                if (plan.getId().equals(workoutId)) {
                    plan.setCompleted(true);
                    dbController.updateUser(user);
                    return true;
                }
            }
        }
        return false;
    }

    public void deleteUser(String userId) {
        dbController.deleteUser(userId);
    }
}