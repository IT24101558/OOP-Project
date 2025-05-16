package com.example.OOP_FitConnect.service;

import com.example.OOP_FitConnect.model.Schedule;
import java.util.List;

public interface ScheduleService {
    List<Schedule> getAllSchedules();
    Schedule getScheduleById(String id);
    List<Schedule> getSchedulesByUserId(String userId);
    Schedule getScheduleByUserIdAndDate(String userId, String date);
    Schedule createSchedule(Schedule schedule);
    Schedule updateSchedule(String id, Schedule schedule);
    Schedule updateWorkoutStatus(String scheduleId, String workoutId, String status);
    boolean deleteSchedule(String id);
}