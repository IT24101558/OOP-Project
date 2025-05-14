package com.example.OOP_FitConnect.controller;

import java.util.Map;
import com.example.OOP_FitConnect.model.User;
import com.example.OOP_FitConnect.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminSettingsController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/settings")
    public String settingsPage(HttpServletRequest request, Model model) {
        String userId = (String) request.getSession().getAttribute("userId");
        User admin = userService.getUserById(userId);
        if (admin != null && admin.isAdmin()) {
            model.addAttribute("admin", admin);
            return "Admin_Settings";
        }
        return "redirect:/login";
    }

    @PostMapping("/admin/settings")
    public String updateSettings(HttpServletRequest request, @RequestParam Map<String, String> params, Model model) {
        // Implement your settings logic here
        String userId = (String) request.getSession().getAttribute("userId");
        User admin = userService.getUserById(userId);
        if (admin != null && admin.isAdmin()) {
            // Update settings as needed
            model.addAttribute("admin", admin);
            model.addAttribute("success", "Settings updated!");
            return "Admin_Settings";
        }
        return "redirect:/login";
    }
}