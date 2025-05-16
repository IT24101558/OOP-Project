package com.example.OOP_FitConnect.repository;

import com.example.OOP_FitConnect.model.Progress;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Repository
public class ProgressRepository {
    // Using CopyOnWriteArrayList for thread safety
    private final List<Progress> progressList;

    public ProgressRepository() {
        this.progressList = new CopyOnWriteArrayList<>();
        // No initial data in the original implementation
    }

    public List<Progress> getAllProgress() {
        return new ArrayList<>(progressList);
    }

    public Progress getProgressById(String id) {
        return progressList.stream()
                .filter(progress -> progress.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Progress> getProgressByUserId(String userId) {
        return progressList.stream()
                .filter(progress -> progress.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public Progress createProgress(Progress progress) {
        if (progress.getId() == null || progress.getId().isEmpty()) {
            progress.setId(UUID.randomUUID().toString());
        }

        progressList.add(progress);
        return progress;
    }

    public Progress updateProgress(String id, Progress updatedProgress) {
        for (int i = 0; i < progressList.size(); i++) {
            if (progressList.get(i).getId().equals(id)) {
                updatedProgress.setId(id);
                progressList.set(i, updatedProgress);
                return updatedProgress;
            }
        }
        return null;
    }

    public boolean deleteProgress(String id) {
        return progressList.removeIf(progress -> progress.getId().equals(id));
    }
}