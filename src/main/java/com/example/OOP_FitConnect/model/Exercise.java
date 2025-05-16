package com.example.OOP_FitConnect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Exercise {
    private String id;
    private String name;
    private String type;
    private String category;
    private String difficulty;
    private String targetMuscles;
    private String imageUrl;
    private String description;
}
