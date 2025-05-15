package com.example.OOP_FitConnect.controller;

import com.example.OOP_FitConnect.model.Supplement;
import com.example.OOP_FitConnect.service.SupplementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/supplements")
public class SupplementController {

    private final SupplementService supplementService;

    @Autowired
    public SupplementController(SupplementService supplementService) {
        this.supplementService = supplementService;
    }

    @GetMapping
    public String viewSupplements(Model model) {
        List<Supplement> supplements = supplementService.getAllSupplements();
        model.addAttribute("supplements", supplements);
        return "supplements"; // This assumes you have a Thymeleaf template named "supplements.html"
    }

    @GetMapping("/api")
    @ResponseBody
    public List<Supplement> getSupplementsAsJson() {
        return supplementService.getAllSupplements();
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public Supplement getSupplementByIdAsJson(@PathVariable Long id) {
        return supplementService.getSupplementById(id);
    }

    // You can add more controller methods for handling other requests (e.g., adding supplements)
}
