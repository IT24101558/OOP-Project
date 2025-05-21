package com.example.OOP_FitConnect.service;

import com.example.OOP_FitConnect.model.Plan;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanService {
    private static final String CSV_FILE_PATH = "data/plans.csv";
    private static final String CSV_HEADER = "id,name,price,description,features";

    public PlanService() {
        initializeCsvFile();
    }

    private void initializeCsvFile() {
        try {
            Path path = Paths.get(CSV_FILE_PATH);
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.write(path, (CSV_HEADER + "\n").getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize plans CSV file", e);
        }
    }

    public List<Plan> getAllPlans() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(CSV_FILE_PATH));
            return lines.stream()
                    .skip(1) // Skip header
                    .filter(StringUtils::hasText)
                    .map(this::parsePlanFromCsv)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read plans from CSV", e);
        }
    }

    public Plan getPlanById(String id) {
        return getAllPlans().stream()
                .filter(plan -> plan.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Plan not found"));
    }

    public Plan createPlan(Plan plan) {
        try {
            String csvLine = convertPlanToCsv(plan);
            Files.write(Paths.get(CSV_FILE_PATH), (csvLine + "\n").getBytes(), java.nio.file.StandardOpenOption.APPEND);
            return plan;
        } catch (IOException e) {
            throw new RuntimeException("Failed to create plan", e);
        }
    }

    public Plan updatePlan(String id, Plan updatedPlan) {
        List<Plan> plans = getAllPlans();
        boolean found = false;

        for (int i = 0; i < plans.size(); i++) {
            if (plans.get(i).getId().equals(id)) {
                updatedPlan.setId(id);
                plans.set(i, updatedPlan);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new RuntimeException("Plan not found");
        }

        try {
            List<String> lines = new ArrayList<>();
            lines.add(CSV_HEADER);
            lines.addAll(plans.stream()
                    .map(this::convertPlanToCsv)
                    .collect(Collectors.toList()));
            Files.write(Paths.get(CSV_FILE_PATH), lines);
            return updatedPlan;
        } catch (IOException e) {
            throw new RuntimeException("Failed to update plan", e);
        }
    }

    public void deletePlan(String id) {
        List<Plan> plans = getAllPlans();
        boolean removed = plans.removeIf(plan -> plan.getId().equals(id));

        if (!removed) {
            throw new RuntimeException("Plan not found");
        }

        try {
            List<String> lines = new ArrayList<>();
            lines.add(CSV_HEADER);
            lines.addAll(plans.stream()
                    .map(this::convertPlanToCsv)
                    .collect(Collectors.toList()));
            Files.write(Paths.get(CSV_FILE_PATH), lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete plan", e);
        }
    }

    private Plan parsePlanFromCsv(String csvLine) {
        String[] parts = csvLine.split(",");
        Plan plan = new Plan();
        plan.setId(parts[0]);
        plan.setName(parts[1]);
        plan.setPrice(Double.parseDouble(parts[2]));
        plan.setDescription(parts[3]);
        plan.setFeatures(List.of(parts[4].split("\\|")));
        return plan;
    }

    private String convertPlanToCsv(Plan plan) {
        return String.join(",",
                plan.getId(),
                plan.getName(),
                String.valueOf(plan.getPrice()),
                plan.getDescription(),
                String.join("|", plan.getFeatures())
        );
    }
}