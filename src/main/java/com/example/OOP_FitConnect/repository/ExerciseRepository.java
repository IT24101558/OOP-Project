package com.example.OOP_FitConnect.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.OOP_FitConnect.model.Exercise;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class ExerciseRepository {
    private final String dataFilePath = "src/main/resources/data/exercises.txt";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ExerciseRepository() {
        initializeDataFile();
    }

    private void initializeDataFile() {
        try {
            File file = new File(dataFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();

                // Create initial data
                List<Exercise> initialExercises = new ArrayList<>();
                initialExercises.add(new Exercise("ex-001", "Bench Press", "Strength", "Strength Training", "Intermediate", "Chest, Triceps, Shoulders", "https://images.unsplash.com/photo-1597452485669-2c7bb5fef90d", "A compound exercise that primarily targets the chest muscles along with the shoulders and triceps."));
                initialExercises.add(new Exercise("ex-002", "Barbell Squats", "Strength", "Strength Training", "Intermediate", "Quadriceps, Hamstrings, Glutes", "https://images.unsplash.com/photo-1434608519344-49d77a699e1d", "A compound lower body exercise that targets the quadriceps, hamstrings, and glutes."));
                initialExercises.add(new Exercise("ex-003", "Pull-Ups", "Strength", "Strength Training", "Advanced", "Back, Biceps, Shoulders", "https://images.unsplash.com/photo-1599058917765-a780eda07a3e", "A bodyweight exercise that primarily targets the upper back and biceps."));

                saveAllExercises(initialExercises);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Exercise> getAllExercises() {
        try {
            File file = new File(dataFilePath);
            if (!file.exists() || file.length() == 0) {
                return new ArrayList<>();
            }

            String content = new String(Files.readAllBytes(Paths.get(dataFilePath)));
            if (content.isEmpty()) {
                return new ArrayList<>();
            }

            return objectMapper.readValue(content, new TypeReference<List<Exercise>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Exercise getExerciseById(String id) {
        List<Exercise> exercises = getAllExercises();
        return exercises.stream()
                .filter(exercise -> exercise.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Exercise> getExercisesByCategory(String category) {
        List<Exercise> exercises = getAllExercises();
        return exercises.stream()
                .filter(exercise -> exercise.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public Exercise createExercise(Exercise exercise) {
        List<Exercise> exercises = getAllExercises();

        if (exercise.getId() == null || exercise.getId().isEmpty()) {
            exercise.setId(UUID.randomUUID().toString());
        }

        exercises.add(exercise);
        saveAllExercises(exercises);
        return exercise;
    }

    public Exercise updateExercise(String id, Exercise updatedExercise) {
        List<Exercise> exercises = getAllExercises();

        for (int i = 0; i < exercises.size(); i++) {
            if (exercises.get(i).getId().equals(id)) {
                updatedExercise.setId(id);
                exercises.set(i, updatedExercise);
                saveAllExercises(exercises);
                return updatedExercise;
            }
        }

        return null;
    }

    public boolean deleteExercise(String id) {
        List<Exercise> exercises = getAllExercises();
        boolean removed = exercises.removeIf(exercise -> exercise.getId().equals(id));

        if (removed) {
            saveAllExercises(exercises);
        }

        return removed;
    }

    private void saveAllExercises(List<Exercise> exercises) {
        try {
            File file = new File(dataFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            String json = objectMapper.writeValueAsString(exercises);
            Files.write(Paths.get(dataFilePath), json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
