package com.example.OOP_FitConnect;



import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @PostMapping
    public ResponseEntity<String> handleContactForm(@RequestBody ContactForm form) {
        // You can add logic to save this data or send an email, etc.
        System.out.println("Received contact form: " + form);
        return ResponseEntity.ok("Form submitted successfully!");
    }
}

