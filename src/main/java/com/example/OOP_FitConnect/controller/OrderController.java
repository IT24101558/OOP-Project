package com.example.OOP_FitConnect.controller;

import com.example.OOP_FitConnect.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order-history")
    public String orderHistoryPage() {
        return "order-history";
    }

    @PostMapping("/api/orders")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody Map<String, Object> orderData) {
         System.out.println("RAW orderData: " + orderData);
        try {
            orderService.saveOrder(orderData);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Order placed successfully!"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Failed to place order: " + e.getMessage()
            ));
        }
    }

    @GetMapping("/api/orders")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> getOrders() {
        try {
            List<Map<String, Object>> orders = orderService.getAllOrders();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(List.of());
        }
    }
} 