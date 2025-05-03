package com.example.OOP_FitConnect.controller;

import com.example.OOP_FitConnect.model.User;
import com.example.OOP_FitConnect.model.WorkoutPlan;
import com.example.OOP_FitConnect.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            String userId = (String) session.getAttribute("userId");
            User user = userService.getUserById(userId);

            model.addAttribute("user", user);

            // Get workout plans based on user role
            List<WorkoutPlan> workoutPlans = user.isAdmin() ? getAllWorkoutPlans() : userService.getUserWorkoutPlans(userId);
            model.addAttribute("workoutPlans", workoutPlans);

            // Calculate progress statistics
            int totalWorkouts = workoutPlans.size();
            int completedWorkouts = (int) workoutPlans.stream()
                    .filter(WorkoutPlan::isCompleted)
                    .count();

            double completionRate = totalWorkouts > 0
                    ? (double) completedWorkouts / totalWorkouts * 100
                    : 0;

            model.addAttribute("totalWorkouts", totalWorkouts);
            model.addAttribute("completedWorkouts", completedWorkouts);
            model.addAttribute("completionRate", Math.round(completionRate));

            return "dashboard";
        }
        return "redirect:/login";
    }

    private List<WorkoutPlan> getAllWorkoutPlans() {
        List<WorkoutPlan> allPlans = new ArrayList<>();
        for (User user : userService.getAllUsers()) {
            allPlans.addAll(user.getWorkoutPlans());
        }
        return allPlans;
    }

    @PostMapping("/api/add-workout")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addWorkout(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String schedule,
            HttpServletRequest request) {

        Map<String, Object> response = new HashMap<>();

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            String userId = (String) session.getAttribute("userId");
            User user = userService.getUserById(userId);

            WorkoutPlan workoutPlan = new WorkoutPlan();
            workoutPlan.setName(name);
            workoutPlan.setDescription(description);
            workoutPlan.setSchedule(schedule);
            workoutPlan.setCompleted(false);

            if (!user.isAdmin()) {
                userService.addWorkoutPlan(userId, workoutPlan);
            } else {
                // For admin, associate with a specific user (e.g., first user for simplicity)
                List<User> users = userService.getAllUsers();
                if (!users.isEmpty()) {
                    users.get(0).addWorkoutPlan(workoutPlan);
                    userService.getUserById(users.get(0).getId());
                }
            }

            response.put("success", true);
            response.put("message", "Workout plan added successfully");
            response.put("workoutPlan", workoutPlan);

            return ResponseEntity.ok(response);
        }

        response.put("success", false);
        response.put("message", "Not authenticated");
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/api/complete-workout")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> completeWorkout(
            @RequestParam String workoutId,
            HttpServletRequest request) {

        Map<String, Object> response = new HashMap<>();

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            String userId = (String) session.getAttribute("userId");
            User user = userService.getUserById(userId);

            if (user.isAdmin() || userService.canAccessWorkout(userId, workoutId)) {
                boolean updated = userService.completeWorkout(userId, workoutId);

                if (updated) {
                    response.put("success", true);
                    response.put("message", "Workout marked as completed");
                    return ResponseEntity.ok(response);
                } else {
                    response.put("success", false);
                    response.put("message", "Workout not found");
                    return ResponseEntity.badRequest().body(response);
                }
            } else {
                response.put("success", false);
                response.put("message", "Unauthorized access to workout");
                return ResponseEntity.badRequest().body(response);
            }
        }

        response.put("success", false);
        response.put("message", "Not authenticated");
        return ResponseEntity.badRequest().body(response);
    }
}