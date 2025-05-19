package com.example.OOP_FitConnect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExerciseController {

    @GetMapping("/exercises")
    public String showExercisesPage() {
        return "exercises";
    }

    @GetMapping("/exercises/boxing-rings")
    public String showBoxingRingsDetails() {
        return "Boxing rings Details";
    }

    @GetMapping("/exercises/crossfit")
    public String showCrossFitDetails() {
        return "CrossFit Details";
    }

    @GetMapping("/exercises/cycling")
    public String showCyclingDetails() {
        return "Cycling Details";
    }

    @GetMapping("/exercises/flexibility-yoga")
    public String showFlexibilityYogaDetails() {
        return "Flexibility & Yoga Details";
    }

    @GetMapping("/exercises/featured")
    public String showFeaturedExercises() {
        return "Featured Exercises";
    }

    @GetMapping("/exercises/hiit-workouts")
    public String showHIITWorkoutsDetails() {
        return "HIIT Workouts Details";
    }

    @GetMapping("/exercises/popular-workouts")
    public String showPopularWorkouts() {
        return "Popular Workouts";
    }

    @GetMapping("/exercises/strength-training")
    public String showStrengthTrainingDetails() {
        return "Strength Training Details";
    }

    @GetMapping("/exercises/popular-categories")
    public String showPopularCategories() {
        return "popular-categories";
    }
} 