package com.example.OOP_FitConnect.service;

import com.example.OOP_FitConnect.model.PaymentHistory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentHistoryService {
    private final List<PaymentHistory> paymentHistory = new ArrayList<>();
    private int nextId = 1;

    public PaymentHistoryService() {
        // Initialize with some sample data
        addSampleData();
    }

    private void addSampleData() {
        // Sample membership payment
        List<PaymentHistory.PaymentItem> membershipItems = new ArrayList<>();
        membershipItems.add(new PaymentHistory.PaymentItem("Premium Plan", 35000.00, 1));
        
        paymentHistory.add(new PaymentHistory(
            "ORD" + String.format("%03d", nextId++),
            "Sanith Wijesinghe",
            "membership",
            35000.00,
            LocalDateTime.now().minusDays(1),
            "Credit Card",
            "completed",
            membershipItems
        ));

        // Sample supplement payment
        List<PaymentHistory.PaymentItem> supplementItems = new ArrayList<>();
        supplementItems.add(new PaymentHistory.PaymentItem("Whey Protein", 30000.00, 1));
        supplementItems.add(new PaymentHistory.PaymentItem("BCAA Powder", 15000.00, 2));
        
        paymentHistory.add(new PaymentHistory(
            "ORD" + String.format("%03d", nextId++),
            "Rozzana Chandler",
            "supplement",
            60000.00,
            LocalDateTime.now().minusDays(2),
            "Debit Card",
            "pending",
            supplementItems
        ));
    }

    public List<PaymentHistory> getAllPayments() {
        return new ArrayList<>(paymentHistory);
    }

    public List<PaymentHistory> getPaymentsByType(String type) {
        if (type == null || type.isEmpty()) {
            return getAllPayments();
        }
        return paymentHistory.stream()
                .filter(payment -> payment.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    public List<PaymentHistory> getPaymentsByStatus(String status) {
        if (status == null || status.isEmpty()) {
            return getAllPayments();
        }
        return paymentHistory.stream()
                .filter(payment -> payment.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    public List<PaymentHistory> searchPayments(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return getAllPayments();
        }
        String lowerSearchTerm = searchTerm.toLowerCase();
        return paymentHistory.stream()
                .filter(payment -> 
                    payment.getId().toLowerCase().contains(lowerSearchTerm) ||
                    payment.getCustomerName().toLowerCase().contains(lowerSearchTerm) ||
                    payment.getPaymentMethod().toLowerCase().contains(lowerSearchTerm))
                .collect(Collectors.toList());
    }

    public void addPayment(PaymentHistory payment) {
        payment.setId("ORD" + String.format("%03d", nextId++));
        paymentHistory.add(payment);
    }
} 