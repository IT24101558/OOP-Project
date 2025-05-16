package com.example.OOP_FitConnect.repository;

import com.example.OOP_FitConnect.model.Schedule;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Repository
public class ScheduleRepository {
    // Using CopyOnWriteArrayList for thread safety
    private final List<Schedule> schedules;

    public ScheduleRepository() {
        this.schedules = new CopyOnWriteArrayList<>();
    }

    public List<Schedule> getAllSchedules() {
        return new ArrayList<>(schedules);
    }

    public Schedule getScheduleById(String id) {
        return schedules.stream()
                .filter(schedule -> schedule.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Schedule> getSchedulesByUserId(String userId) {
        return schedules.stream()
                .filter(schedule -> schedule.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public Schedule getScheduleByUserIdAndDate(String userId, String date) {
        return schedules.stream()
                .filter(schedule -> schedule.getUserId().equals(userId) && schedule.getDate().equals(date))
                .findFirst()
                .orElse(null);
    }

    public Schedule createSchedule(Schedule schedule) {
        if (schedule.getId() == null || schedule.getId().isEmpty()) {
            schedule.setId(UUID.randomUUID().toString());
        }

        schedules.add(schedule);
        return schedule;
    }

    public Schedule updateSchedule(String id, Schedule updatedSchedule) {
        for (int i = 0; i < schedules.size(); i++) {
            if (schedules.get(i).getId().equals(id)) {
                updatedSchedule.setId(id);
                schedules.set(i, updatedSchedule);
                return updatedSchedule;
            }
        }
        return null;
    }

    public Schedule updateWorkoutStatus(String scheduleId, String workoutId, String status) {
        Schedule schedule = getScheduleById(scheduleId);
        if (schedule != null) {
            List<Map<String, Object>> workouts = schedule.getWorkouts();
            for (Map<String, Object> workout : workouts) {
                if (workoutId.equals(workout.get("id"))) {
                    workout.put("status", status);
                    return updateSchedule(scheduleId, schedule);
                }
            }
        }
        return null;
    }

    public boolean deleteSchedule(String id) {
        return schedules.removeIf(schedule -> schedule.getId().equals(id));
    }
}