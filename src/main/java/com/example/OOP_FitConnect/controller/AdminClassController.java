package com.example.OOP_FitConnect.controller;

import com.example.OOP_FitConnect.model.ClassSession;
import com.example.OOP_FitConnect.service.ClassSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/api/classes")
public class AdminClassController {
    private final ClassSessionService classService;

    @Autowired
    public AdminClassController(ClassSessionService classService) {
        this.classService = classService;
    }

    @GetMapping
    public List<ClassSession> getAllClasses() {
        return classService.getAllClasses();
    }

    @PostMapping
    public void addClass(@RequestBody ClassSession cs) {
        cs.setId(UUID.randomUUID().toString());
        classService.addClass(cs);
    }

    @DeleteMapping("/{id}")
    public void deleteClass(@PathVariable String id) {
        classService.deleteClass(id);
    }
}