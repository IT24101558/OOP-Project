package com.example.OOP_FitConnect.service;

import com.example.OOP_FitConnect.model.Exercise;
import java.util.List;

public interface ExerciseService {
    List<Exercise> getAllExercises();
    Exercise getExerciseById(String id);
    List<Exercise> getExercisesByCategory(String category);
    Exercise createExercise(Exercise exercise);
    Exercise updateExercise(String id, Exercise exercise);
    boolean deleteExercise(String id);
}
