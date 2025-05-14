package com.example.OOP_FitConnect.model;

import java.util.UUID;

public class Equipment {
    private String id;
    private String name;
    private String description;
    private int quantity;

    public Equipment() {
        this.id = UUID.randomUUID().toString();
    }

    // Getters and setters...
    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}