package com.example.OOP_FitConnect.service;

import com.example.OOP_FitConnect.model.Exercise;
import com.example.OOP_FitConnect.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public List<Exercise> getAllExercises() {
        return exerciseRepository.getAllExercises();
    }

    public Exercise getExerciseById(String id) {
        return exerciseRepository.getExerciseById(id);
    }

    public List<Exercise> getExercisesByCategory(String category) {
        return exerciseRepository.getExercisesByCategory(category);
    }

    public Exercise createExercise(Exercise exercise) {
        return exerciseRepository.createExercise(exercise);
    }

    public Exercise updateExercise(String id, Exercise exercise) {
        return exerciseRepository.updateExercise(id, exercise);
    }

    public boolean deleteExercise(String id) {
        return exerciseRepository.deleteExercise(id);
    }
}