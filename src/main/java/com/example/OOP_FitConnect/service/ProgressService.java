package com.example.OOP_FitConnect.service;

import com.example.OOP_FitConnect.model.Progress;
import java.util.*;

public class ProgressService {

    private final Map<String, Progress> progressStore = new HashMap<>();

    public List<Progress> getAllProgress() {
        return new ArrayList<>(progressStore.values());
    }

    public Progress getProgressById(String id) {
        return progressStore.get(id);
    }

    public List<Progress> getProgressByUserId(String userId) {
        List<Progress> userProgress = new ArrayList<>();
        for (Progress progress : progressStore.values()) {
            if (progress.getUserId().equals(userId)) {
                userProgress.add(progress);
            }
        }
        return userProgress;
    }

    public Progress createProgress(Progress progress) {
        String id = UUID.randomUUID().toString();
        progress.setId(id);
        progressStore.put(id, progress);
        return progress;
    }

    public Progress updateProgress(String id, Progress progress) {
        if (progressStore.containsKey(id)) {
            progress.setId(id);
            progressStore.put(id, progress);
            return progress;
        }
        return null;
    }

    public boolean deleteProgress(String id) {
        return progressStore.remove(id) != null;
    }
}