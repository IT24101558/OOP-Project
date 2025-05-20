package com.example.OOP_FitConnect.service;

import com.example.OOP_FitConnect.model.Plan; // Changed import
import com.example.OOP_FitConnect.model.PlanHistory; // Changed import
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PlanService {

    private final List<Plan> plans = new ArrayList<>();
    private final List<PlanHistory> planHistoryList = new ArrayList<>(); // In-memory storage for plan history
    private final AtomicLong planIdGenerator = new AtomicLong(1);
    private final AtomicLong planHistoryIdGenerator = new AtomicLong(1);


    public List<Plan> getAllPlans() {
        return plans;
    }

    public Plan getPlanById(Long id) {
        return plans.stream().filter(plan -> plan.getId().equals(id)).findFirst().orElse(null);
    }

    public Plan createPlan(Plan plan) {
        long newPlanId = planIdGenerator.getAndIncrement();
        plan.setId(newPlanId);
        plans.add(plan);

        // Create a PlanHistory entry
        PlanHistory planHistory = new PlanHistory(
                planHistoryIdGenerator.getAndIncrement(),
                plan.getName(),
                plan.getPrice(),
                plan.getDescription(),
                String.join(",", plan.getFeatures())
        );
        planHistoryList.add(planHistory);

        return plan;
    }

    public Plan updatePlan(Long id, Plan plan) {
        Plan existingPlan = getPlanById(id);
        if (existingPlan == null) {
            return null;
        }
        existingPlan.setName(plan.getName());
        existingPlan.setPrice(plan.getPrice());
        existingPlan.setDescription(plan.getDescription());
        existingPlan.setFeatures(plan.getFeatures());
        return existingPlan; //return the updated plan
    }

    public void deletePlan(Long id) {
        plans.removeIf(plan -> plan.getId().equals(id));
    }

    public List<PlanHistory> getPlanHistory() {
        return planHistoryList;
    }
}
