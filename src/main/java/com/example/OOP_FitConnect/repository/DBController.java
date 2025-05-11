package com.example.OOP_FitConnect.repository;

import com.example.OOP_FitConnect.model.User;
import org.springframework.stereotype.Repository;

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

    private void loadFromCSV() {
        File file = new File(CSV_FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            // First line: number of users (skip or use for validation)
            line = br.readLine();
            // Second line: header (skip)
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
            // Write number of users
            pw.println(usersById.size());
            // Write header
            pw.println("id,name,email,branch,verificationToken,resetToken");
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
        return String.join(",",
                safe(user.getId()),
                safe(user.getName()),
                safe(user.getEmail()),
                safe(user.getBranch()),
                safe(user.getVerificationToken()),
                safe(user.getResetToken())
        );
    }

    private User userFromCSV(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length < 6) return null;

        User user = new User();
        user.setId(parts[0]);
        user.setName(parts[1]);
        user.setEmail(parts[2]);
        user.setBranch(parts[3]);
        user.setVerificationToken(parts[4].isEmpty() ? null : parts[4]);
        user.setResetToken(parts[5].isEmpty() ? null : parts[5]);
        return user;
    }

    private String safe(String s) {
        return s == null ? "" : s.replace(",", ""); // simple escaping, improve if needed
    }
}