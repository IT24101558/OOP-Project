package com.example.OOP_FitConnect.service;

import com.example.OOP_FitConnect.model.Schedule;
import java.util.*;

public class ScheduleService {

    private final Map<String, Schedule> scheduleStore = new HashMap<>();

    public List<Schedule> getAllSchedules() {
        return new ArrayList<>(scheduleStore.values());
    }

    public Schedule getScheduleById(String id) {
        return scheduleStore.get(id);
    }

    public List<Schedule> getSchedulesByUserId(String userId) {
        List<Schedule> userSchedules = new ArrayList<>();
        for (Schedule schedule : scheduleStore.values()) {
            if (schedule.getUserId().equals(userId)) {
                userSchedules.add(schedule);
            }
        }
        return userSchedules;
    }

    public Schedule getScheduleByUserIdAndDate(String userId, String date) {
        for (Schedule schedule : scheduleStore.values()) {
            if (schedule.getUserId().equals(userId) && schedule.getDate().equals(date)) {
                return schedule;
            }
        }
        return null;
    }

    public Schedule createSchedule(Schedule schedule) {
        String id = UUID.randomUUID().toString();
        schedule.setId(id);
        scheduleStore.put(id, schedule);
        return schedule;
    }

    public Schedule updateSchedule(String id, Schedule schedule) {
        if (scheduleStore.containsKey(id)) {
            schedule.setId(id);
            scheduleStore.put(id, schedule);
            return schedule;
        }
        return null;
    }

    public Schedule updateWorkoutStatus(String scheduleId, String workoutId, String status) {
        Schedule schedule = scheduleStore.get(scheduleId);
        if (schedule != null) {
            // According to the error, workouts are stored as a List<Map<String, Object>> in Schedule
            List<Map<String, Object>> workouts = schedule.getWorkouts();
            if (workouts != null) {
                // Find the workout with the given ID and update its status
                for (Map<String, Object> workout : workouts) {
                    if (workout.containsKey("id") && workout.get("id").equals(workoutId)) {
                        workout.put("status", status);
                        break;
                    }
                }
                schedule.setWorkouts(workouts);
            }
            return schedule;
        }
        return null;
    }

    public boolean deleteSchedule(String id) {
        return scheduleStore.remove(id) != null;
    }
}