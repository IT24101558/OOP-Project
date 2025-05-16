package com.example.OOP_FitConnect.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.OOP_FitConnect.model.Schedule;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class ScheduleRepository {
    private final String dataFilePath = "src/main/resources/data/schedules.txt";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ScheduleRepository() {
        initializeDataFile();
    }

    private void initializeDataFile() {
        try {
            File file = new File(dataFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                saveAllSchedules(new ArrayList<>());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Schedule> getAllSchedules() {
        try {
            File file = new File(dataFilePath);
            if (!file.exists() || file.length() == 0) {
                return new ArrayList<>();
            }

            String content = new String(Files.readAllBytes(Paths.get(dataFilePath)));
            if (content.isEmpty()) {
                return new ArrayList<>();
            }

            return objectMapper.readValue(content, new TypeReference<List<Schedule>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Schedule getScheduleById(String id) {
        List<Schedule> schedules = getAllSchedules();
        return schedules.stream()
                .filter(schedule -> schedule.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Schedule> getSchedulesByUserId(String userId) {
        List<Schedule> schedules = getAllSchedules();
        return schedules.stream()
                .filter(schedule -> schedule.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public Schedule getScheduleByUserIdAndDate(String userId, String date) {
        List<Schedule> schedules = getAllSchedules();
        return schedules.stream()
                .filter(schedule -> schedule.getUserId().equals(userId) && schedule.getDate().equals(date))
                .findFirst()
                .orElse(null);
    }

    public Schedule createSchedule(Schedule schedule) {
        List<Schedule> schedules = getAllSchedules();

        if (schedule.getId() == null || schedule.getId().isEmpty()) {
            schedule.setId(UUID.randomUUID().toString());
        }

        schedules.add(schedule);
        saveAllSchedules(schedules);
        return schedule;
    }

    public Schedule updateSchedule(String id, Schedule updatedSchedule) {
        List<Schedule> schedules = getAllSchedules();

        for (int i = 0; i < schedules.size(); i++) {
            if (schedules.get(i).getId().equals(id)) {
                updatedSchedule.setId(id);
                schedules.set(i, updatedSchedule);
                saveAllSchedules(schedules);
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
        List<Schedule> schedules = getAllSchedules();
        boolean removed = schedules.removeIf(schedule -> schedule.getId().equals(id));

        if (removed) {
            saveAllSchedules(schedules);
        }

        return removed;
    }

    private void saveAllSchedules(List<Schedule> schedules) {
        try {
            File file = new File(dataFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            String json = objectMapper.writeValueAsString(schedules);
            Files.write(Paths.get(dataFilePath), json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}