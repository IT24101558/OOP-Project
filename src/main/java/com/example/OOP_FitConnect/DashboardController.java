package com.example.OOP_FitConnect;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @GetMapping
    public ResponseEntity<?> getDashboard() {
        Map<String, Object> dashboardData = new HashMap<>();
        dashboardData.put("workoutPlans", getWorkoutPlans());
        dashboardData.put("progressStats", getProgressStats());
        return ResponseEntity.ok(dashboardData);
    }

    private Map<String, Object> getWorkoutPlans() {
        Map<String, Object> plans = new HashMap<>();
        plans.put("beginner", "30-minute daily workout for beginners");
        plans.put("intermediate", "45-minute workout with cardio and strength training");
        plans.put("advanced", "60-minute intensive workout for experienced users");
        return plans;
    }

    private Map<String, Object> getProgressStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("workoutsCompleted", 12);
        stats.put("caloriesBurned", 3500);
        stats.put("minutesExercised", 360);
        stats.put("achievements", new String[]{"First Workout", "Week Streak", "Month Milestone"});
        return stats;
    }
}
