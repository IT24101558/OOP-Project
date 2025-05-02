package com.example.OOP_FitConnect;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bmi")
@CrossOrigin // Allow requests from frontend (if needed)
public class BMICalculatorController {

    @PostMapping
    public ResponseEntity<String> calculateBMI(
            @RequestParam double weight,
            @RequestParam double height,
            @RequestParam String gender) {

        if (weight <= 0 || height <= 0 || gender == null || gender.isEmpty()) {
            return ResponseEntity.badRequest().body("Please provide valid inputs.");
        }

        double heightInMeters = height / 100.0;
        double bmi = weight / (heightInMeters * heightInMeters);

        String category;
        if (bmi < 18.5) {
            category = "Underweight";
        } else if (bmi < 24.9) {
            category = "Normal weight";
        } else if (bmi < 29.9) {
            category = "Overweight";
        } else {
            category = "Obese";
        }

        String result = String.format("Your BMI is %.1f (%s)", bmi, category);
        return ResponseEntity.ok(result);
    }
}

