package com.example.OOP_FitConnect.controller;

import com.example.OOP_FitConnect.model.MembershipPlan;
import com.example.OOP_FitConnect.model.User;
import com.example.OOP_FitConnect.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class MembershipController {

    @Autowired
    private GuestService guestService;

    private final List<MembershipPlan> membershipPlans = new ArrayList<>();
    private final List<PlanHistoryRecord> planHistory = new ArrayList<>();
    private int nextPlanId = 1;
    private int nextHistoryId = 1;
    // private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");

    public MembershipController() {
        // Initialize with some data
        membershipPlans.add(new MembershipPlan(nextPlanId++, "Basic", "Basic Plan", 10000.00, 12));
        membershipPlans.add(new MembershipPlan(nextPlanId++, "Standard", "Standard Plan", 25000.00, 12));
        membershipPlans.add(new MembershipPlan(nextPlanId++, "Premium", "Premium Plan", 35000.00, 24));

        planHistory
                .add(new PlanHistoryRecord(nextHistoryId++, LocalDate.of(2025, 5, 6), "Basic", 12, 10000.00, "active"));
        planHistory.add(
                new PlanHistoryRecord(nextHistoryId++, LocalDate.of(2025, 4, 6), "Standard", 12, 25000.00, "active"));
        planHistory.add(
                new PlanHistoryRecord(nextHistoryId++, LocalDate.of(2025, 3, 6), "Basic", 12, 10000.00, "expired"));
        planHistory.add(
                new PlanHistoryRecord(nextHistoryId++, LocalDate.of(2025, 2, 6), "Premium", 24, 35000.00, "inactive"));
        planHistory
                .add(new PlanHistoryRecord(nextHistoryId++, LocalDate.of(2025, 1, 6), "Basic", 12, 10000.00, "active"));
    }

    @GetMapping("/plans")
    public ResponseEntity<List<MembershipPlan>> getAllMembershipPlans() {
        return ResponseEntity.ok(membershipPlans);
    }

    @PostMapping("/plans")
    public ResponseEntity<MembershipPlan> addMembershipPlan(@RequestBody MembershipPlan newPlan) {
        newPlan.setId(nextPlanId++);
        membershipPlans.add(newPlan);
        return new ResponseEntity<>(newPlan, HttpStatus.CREATED);
    }

    @PutMapping("/plans/{id}")
    public ResponseEntity<MembershipPlan> updateMembershipPlan(@PathVariable int id,
                                                               @RequestBody MembershipPlan updatedPlan) {
        Optional<MembershipPlan> planOptional = membershipPlans.stream().filter(p -> p.getId() == id).findFirst();
        if (planOptional.isPresent()) {
            MembershipPlan existingPlan = planOptional.get();
            existingPlan.setName(updatedPlan.getName());
            existingPlan.setDescription(updatedPlan.getDescription());
            existingPlan.setPrice(updatedPlan.getPrice());
            existingPlan.setDuration(updatedPlan.getDuration());
            return ResponseEntity.ok(existingPlan);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/plans/{id}")
    public ResponseEntity<Void> deleteMembershipPlan(@PathVariable int id) {
        boolean removed = membershipPlans.removeIf(plan -> plan.getId() == id);
        if (removed) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/history")
    public ResponseEntity<List<PlanHistoryRecord>> getAllPlanHistory(
            @RequestParam(required = false) String searchTerm) {
        List<PlanHistoryRecord> filteredHistory;
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            String lowerSearchTerm = searchTerm.toLowerCase();
            filteredHistory = planHistory.stream()
                    .filter(record -> record.getFormattedDate().toLowerCase().contains(lowerSearchTerm) ||
                            record.getPlanName().toLowerCase().contains(lowerSearchTerm) ||
                            String.valueOf(record.getDuration()).toLowerCase().contains(lowerSearchTerm) ||
                            String.valueOf(record.getAmount()).toLowerCase().contains(lowerSearchTerm) ||
                            record.getStatus().toLowerCase().contains(lowerSearchTerm))
                    .collect(Collectors.toList());
        } else {
            filteredHistory = planHistory;
        }

        return ResponseEntity.ok(filteredHistory);
    }

    @GetMapping("/monthprogress")
    public String monthProgressPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            String userId = (String) session.getAttribute("userId");
            User user = guestService.getUserById(userId);
            if (user != null) {
                model.addAttribute("user", user);
            }
        }
        return "monthprogress";
    }

    private record PlanHistoryRecord(int i, LocalDate localDate, String standard, int i1, double v, String active) {
        public String getPlanName() {
            return null;
        }

        public char[] getDuration() {
            return null;
        }

        public String getFormattedDate() {
            return null;
        }

        public char[] getAmount() {
            return null;
        }

        public String getStatus() {


            return "";
        }
    }
}

