package com.example.OOP_FitConnect.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Schedule {
    private String id;
    private String userId;
    private String date;
    private List<Map<String, Object>> workouts;

    // Default constructor
    public Schedule() {
        this.workouts = new ArrayList<>();
    }

    // Constructor with parameters
    public Schedule(String id, String userId, String date, List<Map<String, Object>> workouts) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.workouts = workouts != null ? workouts : new ArrayList<>();
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Map<String, Object>> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<Map<String, Object>> workouts) {
        this.workouts = workouts;
    }
}