package com.example.OOP_FitConnect.repository;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitconnect.model.Progress;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class ProgressRepository {
    private final String dataFilePath = "src/main/resources/data/progress.txt";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ProgressRepository() {
        initializeDataFile();
    }

    private void initializeDataFile() {
        try {
            File file = new File(dataFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();

                // Create initial data
                List<Progress> initialProgress = new ArrayList<>();
                initialProgress.add(new Progress("prog-001", "user-001", "2023-05-01", 24, 18350, 1240, 8, 85.0, 14.0, 68.0));

                saveAllProgress(initialProgress);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Progress> getAllProgress() {
        try {
            File file = new File(dataFilePath);
            if (!file.exists() || file.length() == 0) {
                return new ArrayList<>();
            }

            String content = new String(Files.readAllBytes(Paths.get(dataFilePath)));
            if (content.isEmpty()) {
                return new ArrayList<>();
            }

            return objectMapper.readValue(content, new TypeReference<List<Progress>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Progress getProgressById(String id) {
        List<Progress> progressList = getAllProgress();
        return progressList.stream()
                .filter(progress -> progress.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Progress> getProgressByUserId(String userId) {
        List<Progress> progressList = getAllProgress();
        return progressList.stream()
                .filter(progress -> progress.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public Progress createProgress(Progress progress) {
        List<Progress> progressList = getAllProgress();

        if (progress.getId() == null || progress.getId().isEmpty()) {
            progress.setId(UUID.randomUUID().toString());
        }

        progressList.add(progress);
        saveAllProgress(progressList);
        return progress;
    }

    public Progress updateProgress(String id, Progress updatedProgress) {
        List<Progress> progressList = getAllProgress();

        for (int i = 0; i < progressList.size(); i++) {
            if (progressList.get(i).getId().equals(id)) {
                updatedProgress.setId(id);
                progressList.set(i, updatedProgress);
                saveAllProgress(progressList);
                return updatedProgress;
            }
        }

        return null;
    }

    public boolean deleteProgress(String id) {
        List<Progress> progressList = getAllProgress();
        boolean removed = progressList.removeIf(progress -> progress.getId().equals(id));

        if (removed) {
            saveAllProgress(progressList);
        }

        return removed;
    }

    private void saveAllProgress(List<Progress> progressList) {
        try {
            File file = new File(dataFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            String json = objectMapper.writeValueAsString(progressList);
            Files.write(Paths.get(dataFilePath), json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
