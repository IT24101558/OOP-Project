package com.example.OOP_FitConnect.controller;

import com.example.OOP_FitConnect.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VerificationController {

    @Autowired
    private GuestService guestService; //encapsulation

    @GetMapping("/verify") //abstraction
    public String verifyEmail(@RequestParam String token, Model model) {
        boolean verified = guestService.verifyEmail(token);

        if (verified) {
            model.addAttribute("verified", true);
            model.addAttribute("message", "Your email has been verified successfully! You can now login.");
        } else {
            model.addAttribute("verified", false);
            model.addAttribute("message", "Invalid or expired verification token.");
        }

        return "verification-result";
    }
}