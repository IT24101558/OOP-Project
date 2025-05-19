package com.example.OOP_FitConnect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminClassPageController {
    @GetMapping("/classes")
    public String adminClassPage(Model model) {
        return "Admin_class";
    }
} 