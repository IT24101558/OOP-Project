package com.example.OOP_FitConnect.controller;

import com.example.OOP_FitConnect.model.PaymentHistory;
import com.example.OOP_FitConnect.service.PaymentHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class PaymentHistoryController {

    private final PaymentHistoryService paymentHistoryService;

    @Autowired
    public PaymentHistoryController(PaymentHistoryService paymentHistoryService) {
        this.paymentHistoryService = paymentHistoryService;
    }

    @GetMapping("/payment-history")
    public String paymentHistoryPage() {
        return "Admin_payment_history";
    }

    @GetMapping("/api/payments")
    @ResponseBody
    public ResponseEntity<List<PaymentHistory>> getPayments(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search) {
        
        List<PaymentHistory> payments;
        
        if (search != null && !search.isEmpty()) {
            payments = paymentHistoryService.searchPayments(search);
        } else if (type != null && !type.isEmpty()) {
            payments = paymentHistoryService.getPaymentsByType(type);
        } else if (status != null && !status.isEmpty()) {
            payments = paymentHistoryService.getPaymentsByStatus(status);
        } else {
            payments = paymentHistoryService.getAllPayments();
        }
        
        return ResponseEntity.ok(payments);
    }

    @PostMapping("/api/payments")
    @ResponseBody
    public ResponseEntity<PaymentHistory> addPayment(@RequestBody PaymentHistory payment) {
        paymentHistoryService.addPayment(payment);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/api/total-revenue")
    @ResponseBody
    public double getTotalRevenue() {
        return paymentHistoryService.getAllPayments().stream()
                .mapToDouble(PaymentHistory::getAmount)
                .sum();
    }
} 