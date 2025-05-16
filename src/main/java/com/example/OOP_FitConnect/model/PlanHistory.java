package com.example.OOP_FitConnect.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PlanHistory {
    private int id;
    private LocalDate date;
    private String planName;
    private int duration;
    private double amount;
    private String status;
    private final transient DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");

    public PlanHistory() {
    }

    public PlanHistory(int id, LocalDate date, String planName, int duration, double amount, String status) {
        this.id = id;
        this.date = date;
        this.planName = planName;
        this.duration = duration;
        this.amount = amount;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getFormattedDate() {
        return date.format(dateFormatter);
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


