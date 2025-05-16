package com.example.OOP_FitConnect.controller;
import com.fitconnect.model.Progress;
import com.fitconnect.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {
    private final ProgressService progressService;

    @Autowired
    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @GetMapping
    public ResponseEntity<List<Progress>> getAllProgress() {
        return ResponseEntity.ok(progressService.getAllProgress());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Progress> getProgressById(@PathVariable String id) {
        Progress progress = progressService.getProgressById(id);
        if (progress != null) {
            return ResponseEntity.ok(progress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Progress>> getProgressByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(progressService.getProgressByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<Progress> createProgress(@RequestBody Progress progress) {
        return new ResponseEntity<>(progressService.createProgress(progress), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Progress> updateProgress(@PathVariable String id, @RequestBody Progress progress) {
        Progress updatedProgress = progressService.updateProgress(id, progress);
        if (updatedProgress != null) {
            return ResponseEntity.ok(updatedProgress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgress(@PathVariable String id) {
        boolean deleted = progressService.deleteProgress(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}