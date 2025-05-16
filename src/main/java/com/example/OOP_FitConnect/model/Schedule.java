package com.example.OOP_FitConnect.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Schedule {
    private String id;
    private String userId;
    private String date;
    private List<Workout> workouts;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Workout {
        private String id;
        private String name;
        private String type;
        private String startTime;
        private String endTime;
        private int duration;
        private int caloriesBurned;
        private String status;
        private String notes;
    }
}
