package com.example.OOP_FitConnect.model;

import lombok.Getter;
import lombok.Setter;
import java.util.List; // Import the List class

@Getter
@Setter
public class Plan {
    private Long id;
    private String name;
    private double price;
    private String description;
    private List<String> features;
}