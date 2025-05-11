package com.example.OOP_FitConnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping
    public ResponseEntity<String> handleContactForm(@RequestBody Map<String, String> formData) {
        try {
            String firstName = formData.get("firstName");
            String lastName = formData.get("lastName");
            String email = formData.get("email");
            String message = formData.get("message");

            // Compose and send email
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo("admin@example.com");
            mailMessage.setSubject("New Contact from " + firstName + " " + lastName);
            mailMessage.setText("From: " + email + "\n\nMessage:\n" + message);
            mailSender.send(mailMessage);

            return ResponseEntity.ok("Message sent successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sending message: " + e.getMessage());
        }
    }
}
