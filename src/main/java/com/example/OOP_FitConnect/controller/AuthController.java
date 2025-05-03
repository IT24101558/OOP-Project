package com.example.OOP_FitConnect.controller;

import com.example.OOP_FitConnect.model.User;
import com.example.OOP_FitConnect.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage(@RequestParam String token, Model model) {
        if (userService.isValidResetToken(token)) {
            model.addAttribute("token", token);
            return "reset-password";
        }
        return "redirect:/login?error=invalid_token";
    }

    @PostMapping("/api/login")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> login(
            @RequestParam String email,
            @RequestParam String password,
            HttpServletRequest request) {

        Map<String, Object> response = new HashMap<>();

        User user = userService.authenticate(email, password);
        if (user != null) {
            // Create session
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", user.getId());
            session.setAttribute("userEmail", user.getEmail());
            session.setAttribute("userRole", user.getRole());  // Add role to session

            response.put("success", true);
            // Redirect based on role
            response.put("redirect", user.isAdmin() ? "/admin/dashboard" : "/dashboard");

            // Add verification check for non-admin users
            if (!user.isAdmin() && !user.isVerified()) {
                response.put("warning", "Please verify your email to access all features");
            }

            return ResponseEntity.ok(response);
        }

        response.put("success", false);
        response.put("message", "Invalid email or password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @PostMapping("/api/register")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> register(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            HttpServletRequest request) {

        Map<String, Object> response = new HashMap<>();

        // Check if email is already registered
        if (userService.getUserByEmail(email) != null) {
            response.put("success", false);
            response.put("message", "Email already registered");
            return ResponseEntity.badRequest().body(response);
        }

        // Register new user
        String verificationToken = UUID.randomUUID().toString();
        User user = userService.registerUser(name, email, password, verificationToken);

        // Send verification email
        userService.sendVerificationEmail(user, verificationToken);

        response.put("success", true);
        response.put("message", "Registration successful! Please check your email to verify your account.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/forgot-password")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> forgotPassword(@RequestParam String email) {
        Map<String, Object> response = new HashMap<>();

        User user = userService.getUserByEmail(email);
        if (user != null) {
            String resetToken = userService.generatePasswordResetToken(user);
            userService.sendPasswordResetEmail(user, resetToken);

            response.put("success", true);
            response.put("message", "Password reset instructions sent to your email");
            return ResponseEntity.ok(response);
        }

        response.put("success", false);
        response.put("message", "Email not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping("/api/reset-password")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> resetPassword(
            @RequestParam String token,
            @RequestParam String password) {

        Map<String, Object> response = new HashMap<>();

        if (userService.resetPassword(token, password)) {
            response.put("success", true);
            response.put("message", "Password reset successful");
            response.put("redirect", "/login");
            return ResponseEntity.ok(response);
        }

        response.put("success", false);
        response.put("message", "Invalid or expired token");
        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/login";
    }
}