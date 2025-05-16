package com.example.OOP_FitConnect.model;

public class Progress {
    private String id;
    private String userId;
    private String exerciseId;
    private String date;
    private double value;
    private String notes;

    // Default constructor
    public Progress() {
    }

    // Constructor with parameters
    public Progress(String id, String userId, String exerciseId, String date, double value, String notes) {
        this.id = id;
        this.userId = userId;
        this.exerciseId = exerciseId;
        this.date = date;
        this.value = value;
        this.notes = notes;
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

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}