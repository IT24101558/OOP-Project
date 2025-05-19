package com.example.OOP_FitConnect.model;

import java.time.LocalDateTime;
import java.util.List;

public class PaymentHistory {
    private String id;
    private String customerName;
    private String type; // membership or supplement
    private double amount;
    private LocalDateTime date;
    private String paymentMethod;
    private String status;
    private List<PaymentItem> items;

    public PaymentHistory() {
    }

    public PaymentHistory(String id, String customerName, String type, double amount, LocalDateTime date, 
                         String paymentMethod, String status, List<PaymentItem> items) {
        this.id = id;
        this.customerName = customerName;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.items = items;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PaymentItem> getItems() {
        return items;
    }

    public void setItems(List<PaymentItem> items) {
        this.items = items;
    }

    public static class PaymentItem {
        private String name;
        private double price;
        private int quantity;

        public PaymentItem() {
        }

        public PaymentItem(String name, double price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
} 