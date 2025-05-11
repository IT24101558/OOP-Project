package com.example.OOP_FitConnect.repository;

import com.example.OOP_FitConnect.model.User;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class DBController {

    private static final String CSV_FILE = "users.csv";
    private final Map<String, User> usersById = new ConcurrentHashMap<>();
    private final Map<String, User> usersByEmail = new ConcurrentHashMap<>();
    private final Map<String, User> usersByVerificationToken = new ConcurrentHashMap<>();
    private final Map<String, User> usersByResetToken = new ConcurrentHashMap<>();

    public DBController() {
        loadFromCSV();
    }

    @PostConstruct
    private void loadFromCSV() {
        File file = new File(CSV_FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            // Skip header
            br.readLine();
            while ((line = br.readLine()) != null) {
                User user = userFromCSV(line);
                if (user != null) {
                    addUserToMaps(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_FILE))) {
            // Write header (adjust fields as needed)
            pw.println("id,email,verificationToken,resetToken");
            for (User user : usersById.values()) {
                pw.println(userToCSV(user));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addUserToMaps(User user) {
        usersById.put(user.getId(), user);
        usersByEmail.put(user.getEmail(), user);

        if (user.getVerificationToken() != null) {
            usersByVerificationToken.put(user.getVerificationToken(), user);
        }
        if (user.getResetToken() != null) {
            usersByResetToken.put(user.getResetToken(), user);
        }
    }

    private void removeUserFromMaps(User user) {
        usersById.remove(user.getId());
        usersByEmail.remove(user.getEmail());
        if (user.getVerificationToken() != null) {
            usersByVerificationToken.remove(user.getVerificationToken());
        }
        if (user.getResetToken() != null) {
            usersByResetToken.remove(user.getResetToken());
        }
    }

    public synchronized User saveUser(User user) {
        addUserToMaps(user);
        saveToCSV();
        return user;
    }

    public synchronized User updateUser(User user) {
        User existingUser = usersById.get(user.getId());
        if (existingUser != null) {
            removeUserFromMaps(existingUser);
        }
        addUserToMaps(user);
        saveToCSV();
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

    public synchronized void deleteUser(String id) {
        User user = usersById.get(id);
        if (user != null) {
            removeUserFromMaps(user);
            saveToCSV();
        }
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(usersById.values());
    }

    // --- CSV Serialization/Deserialization ---

    private String userToCSV(User user) {
        // Adjust this according to your User fields
        // Escape commas if needed
        return String.join(",",
                safe(user.getId()),
                safe(user.getEmail()),
                safe(user.getVerificationToken()),
                safe(user.getResetToken())
                // add other fields as needed
        );
    }

    private User userFromCSV(String line) {
        // Adjust this according to your User fields
        String[] parts = line.split(",", -1);
        if (parts.length < 4) return null; // adjust if you have more fields

        User user = new User();
        user.setId(parts[0]);
        user.setEmail(parts[1]);
        user.setVerificationToken(parts[2].isEmpty() ? null : parts[2]);
        user.setResetToken(parts[3].isEmpty() ? null : parts[3]);
        // set other fields as needed
        return user;
    }

    private String safe(String s) {
        return s == null ? "" : s.replace(",", ""); // simple escaping, improve if needed
    }
}