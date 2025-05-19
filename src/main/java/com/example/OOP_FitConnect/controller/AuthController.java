package com.example.OOP_FitConnect.controller;

import com.example.OOP_FitConnect.model.User;
import com.example.OOP_FitConnect.service.GuestService;
import jakarta.servlet.http.HttpServletRequest;  //  Still needed for session
import jakarta.servlet.http.HttpSession;      //  Still needed for session
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class AuthController {

    @Autowired
    private GuestService guestService;

    // Guest accessible pages
    @GetMapping("/index")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/gallery")
    public String galleryPage() {
        return "gallery";
    }

    @GetMapping("/memplan")
    public String memplanPage() {
        return "memplan";
    }

    @GetMapping("/UserPlans")
    public String userPlansPage() {
        return "UserPlans";
    }

    @GetMapping("/About_us")
    public String aboutUsPage() {
        return "About_us";
    }

    // Authentication pages
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

    @GetMapping("/verification-result")
    public String verificationResultPage() {
        return "verification-result";
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage(@RequestParam String token, Model model) {
        if (guestService.isValidResetToken(token)) {
            model.addAttribute("token", token);
            return "reset-password";
        }
        return "redirect:/login?error=invalid_token";
    }

    // User and Admin dashboards
    @GetMapping("/user/dashboard")
    public String userDashboard(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            String userId = (String) session.getAttribute("userId");
            User user = guestService.getUserById(userId);
            if (user != null && "USER".equals(user.getRole())) {
                model.addAttribute("user", user);
                return "member_dashboard";
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String adminDashboard(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            String userId = (String) session.getAttribute("userId");
            User admin = guestService.getUserById(userId);
            if (admin != null && "ADMIN".equals(admin.getRole())) {
                model.addAttribute("admin", admin);
                return "admin_dashboard";
            }
        }
        return "redirect:/login";
    }

    // Additional user pages
    @GetMapping("/user/profile")
    public String userProfile(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            String userId = (String) session.getAttribute("userId");
            User user = guestService.getUserById(userId);
            if (user != null && "USER".equals(user.getRole())) {
                model.addAttribute("user", user);
                return "profile";
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/user/schedule")
    public String userSchedule(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            String userId = (String) session.getAttribute("userId");
            User user = guestService.getUserById(userId);
            if (user != null && "USER".equals(user.getRole())) {
                model.addAttribute("user", user);
                return "schedule";
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/user/settings")
    public String userSettings(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            String userId = (String) session.getAttribute("userId");
            User user = guestService.getUserById(userId);
            if (user != null && "USER".equals(user.getRole())) {
                model.addAttribute("user", user);
                return "User-Settings";
            }
        }
        return "redirect:/login";
    }

    @PostMapping("/api/login")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> login(
            @RequestParam String email,
            @RequestParam String password,
            HttpServletRequest request) {

        Map<String, Object> response = new HashMap<>();

        User user = guestService.authenticate(email, password);
        if (user != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", user.getId());
            session.setAttribute("userEmail", user.getEmail());
            session.setAttribute("userRole", user.getRole());

            response.put("success", true);
            String redirectUrl = "/";
            if ("ADMIN".equals(user.getRole())) {
                redirectUrl = "/admin/dashboard";
            } else if ("USER".equals(user.getRole())) {
                redirectUrl = "/user/dashboard";
            }
            response.put("redirect", redirectUrl);
            if (!user.isVerified() && !"ADMIN".equals(user.getRole())) {
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
            @RequestParam String branch,
            HttpServletRequest request) {

        Map<String, Object> response = new HashMap<>();

        if (guestService.getUserByEmail(email) != null) {
            response.put("success", false);
            response.put("message", "Email already registered");
            return ResponseEntity.badRequest().body(response);
        }

        String verificationToken = UUID.randomUUID().toString();
        User user = guestService.registerUser(name, email, password, verificationToken, branch);
        guestService.sendVerificationEmail(user, verificationToken);

        response.put("success", true);
        response.put("message", "Registration successful! Please check your email to verify your account.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/forgot-password")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> forgotPassword(@RequestParam String email) {
        Map<String, Object> response = new HashMap<>();

        User user = guestService.getUserByEmail(email);
        if (user != null) {
            String resetToken = guestService.generatePasswordResetToken(user);
            guestService.sendPasswordResetEmail(user, resetToken);

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

        if (guestService.resetPassword(token, password)) {
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
    public String logout(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        redirectAttributes.addFlashAttribute("message", "Logged out successfully.");
        return "redirect:/login";
    }
}

