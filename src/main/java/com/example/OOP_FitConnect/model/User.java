package com.example.OOP_FitConnect.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private boolean verified;
    private String verificationToken;
    private String branch;
    private String resetToken;
    private long resetTokenExpiry;
    private Double bmi;
    private List<WorkoutPlan> workoutPlans;
    private String role; // Added role field
    private String profileImage; // Added profileImage field
    private Date createdAt; // Added createdAt field

    public User() {
        this.id = UUID.randomUUID().toString();
        this.verified = false;
        this.workoutPlans = new ArrayList<>();
        this.role = "USER"; // Default role
        this.createdAt = new Date(); // Initialize createdAt
    }

    // Existing getters and setters...

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return "ADMIN".equals(role);
    }
    public boolean isGuest() {
        return "GUEST".equals(role);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public long getResetTokenExpiry() {
        return resetTokenExpiry;
    }

    public void setResetTokenExpiry(long resetTokenExpiry) {
        this.resetTokenExpiry = resetTokenExpiry;
    }

    public Double getBmi() {
        return bmi;
    }

    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }

    public List<WorkoutPlan> getWorkoutPlans() {
        return workoutPlans;
    }

    public void setWorkoutPlans(List<WorkoutPlan> workoutPlans) {
        this.workoutPlans = workoutPlans;
    }

    public void addWorkoutPlan(WorkoutPlan workoutPlan) {
        this.workoutPlans.add(workoutPlan);
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getBranch() {return branch; }

    public void setBranch(String branch) {this.branch = branch;}
}
