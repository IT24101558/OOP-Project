package com.example.OOP_FitConnect.model;

public class Exercise {
    private String id;
    private String name;
    private String category;
    private String type;
    private String difficulty;
    private String muscleGroups;
    private String imageUrl;
    private String description;

    // Default constructor
    public Exercise() {
    }

    // Constructor with all fields
    public Exercise(String id, String name, String category, String type, String difficulty, String muscleGroups, String imageUrl, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.muscleGroups = muscleGroups;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    // Getters and Setters
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getMuscleGroups() {
        return muscleGroups;
    }

    public void setMuscleGroups(String muscleGroups) {
        this.muscleGroups = muscleGroups;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}