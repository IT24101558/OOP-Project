package com.example.OOP_FitConnect.model;


import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanHistory { // Not an entity, so no JPA annotations
    private Long id;
    private String name;
    private double price;
    private String description;
    private String features;
    private LocalDateTime createdAt;

    public PlanHistory() {
        this.createdAt = LocalDateTime.now();
    }

    public PlanHistory(Long id, String name, double price, String description, String features) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.features = features;
        this.createdAt = LocalDateTime.now();
    }
}


