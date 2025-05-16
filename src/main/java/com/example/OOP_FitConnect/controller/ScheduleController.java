package com.example.OOP_FitConnect.controller;

import com.fitconnect.model.Schedule;
import com.fitconnect.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation .*;

import java.util.List;
import java.util.Map;

    @RestController
    @RequestMapping("/api/schedules")
    public class ScheduleController {
        private final ScheduleService scheduleService;

        @Autowired
        public ScheduleController(ScheduleService scheduleService) {
            this.scheduleService = scheduleService;
        }

        @GetMapping
        public ResponseEntity<List<Schedule>> getAllSchedules() {
            return ResponseEntity.ok(scheduleService.getAllSchedules());
        }

        @GetMapping("/{id}")
        public ResponseEntity<Schedule> getScheduleById(@PathVariable String id) {
            Schedule schedule = scheduleService.getScheduleById(id);
            if (schedule != null) {
                return ResponseEntity.ok(schedule);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @GetMapping("/user/{userId}")
        public ResponseEntity<List<Schedule>> getSchedulesByUserId(@PathVariable String userId) {
            return ResponseEntity.ok(scheduleService.getSchedulesByUserId(userId));
        }

        @GetMapping("/user/{userId}/date/{date}")
        public ResponseEntity<Schedule> getScheduleByUserIdAndDate(
                @PathVariable String userId,
                @PathVariable String date) {
            Schedule schedule = scheduleService.getScheduleByUserIdAndDate(userId, date);
            if (schedule != null) {
                return ResponseEntity.ok(schedule);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @PostMapping
        public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) {
            return new ResponseEntity<>(scheduleService.createSchedule(schedule), HttpStatus.CREATED);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Schedule> updateSchedule(@PathVariable String id, @RequestBody Schedule schedule) {
            Schedule updatedSchedule = scheduleService.updateSchedule(id, schedule);
            if (updatedSchedule != null) {
                return ResponseEntity.ok(updatedSchedule);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @PatchMapping("/{scheduleId}/workouts/{workoutId}")
        public ResponseEntity<Schedule> updateWorkoutStatus(
                @PathVariable String scheduleId,
                @PathVariable String workoutId,
                @RequestBody Map<String, String> statusUpdate) {

            String status = statusUpdate.get("status");
            if (status == null) {
                return ResponseEntity.badRequest().build();
            }

            Schedule updatedSchedule = scheduleService.updateWorkoutStatus(scheduleId, workoutId, status);
            if (updatedSchedule != null) {
                return ResponseEntity.ok(updatedSchedule);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteSchedule(@PathVariable String id) {
            boolean deleted = scheduleService.deleteSchedule(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    }

