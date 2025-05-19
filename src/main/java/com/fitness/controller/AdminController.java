package com.fitness.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/logout")
    public String showLogoutConfirmation() {
        return "Admin_logout";
    }

    @GetMapping("/admin/logout/confirm")
    public String confirmLogout() {
        return "redirect:/";
    }
} 