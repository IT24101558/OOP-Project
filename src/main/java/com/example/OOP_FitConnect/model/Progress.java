package com.example.OOP_FitConnect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Progress {
    private String id;
    private String userId;
    private String date;
    private int totalWorkouts;
    private int caloriesBurned;
    private int activeMinutes;
    private int achievements;
    private double weight;
    private double bodyFat;
    private double muscleMass;
}
