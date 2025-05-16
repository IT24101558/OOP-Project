package com.example.OOP_FitConnect.repository;

import com.example.OOP_FitConnect.model.Exercise;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Repository
public class ExerciseRepository {
    // Using CopyOnWriteArrayList for thread safety
    private final List<Exercise> exercises;

    public ExerciseRepository() {
        this.exercises = new CopyOnWriteArrayList<>();
        initializeData();
    }

    private void initializeData() {
        // Create initial sample exercises
        if (exercises.isEmpty()) {
            exercises.add(new Exercise("ex-001", "Bench Press", "Strength", "Strength Training", "Intermediate",
                    "Chest, Triceps, Shoulders", "https://images.unsplash.com/photo-1597452485669-2c7bb5fef90d",
                    "A compound exercise that primarily targets the chest muscles along with the shoulders and triceps."));

            exercises.add(new Exercise("ex-002", "Barbell Squats", "Strength", "Strength Training", "Intermediate",
                    "Quadriceps, Hamstrings, Glutes", "https://images.unsplash.com/photo-1434608519344-49d77a699e1d",
                    "A compound lower body exercise that targets the quadriceps, hamstrings, and glutes."));

            exercises.add(new Exercise("ex-003", "Pull-Ups", "Strength", "Strength Training", "Advanced",
                    "Back, Biceps, Shoulders", "https://images.unsplash.com/photo-1599058917765-a780eda07a3e",
                    "A bodyweight exercise that primarily targets the upper back and biceps."));
        }
    }

    public List<Exercise> getAllExercises() {
        return new ArrayList<>(exercises);
    }

    public Exercise getExerciseById(String id) {
        return exercises.stream()
                .filter(exercise -> exercise.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Exercise> getExercisesByCategory(String category) {
        return exercises.stream()
                .filter(exercise -> exercise.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public Exercise createExercise(Exercise exercise) {
        if (exercise.getId() == null || exercise.getId().isEmpty()) {
            exercise.setId(UUID.randomUUID().toString());
        }

        exercises.add(exercise);
        return exercise;
    }

    public Exercise updateExercise(String id, Exercise updatedExercise) {
        for (int i = 0; i < exercises.size(); i++) {
            if (exercises.get(i).getId().equals(id)) {
                updatedExercise.setId(id);
                exercises.set(i, updatedExercise);
                return updatedExercise;
            }
        }
        return null;
    }

    public boolean deleteExercise(String id) {
        return exercises.removeIf(exercise -> exercise.getId().equals(id));
    }
}