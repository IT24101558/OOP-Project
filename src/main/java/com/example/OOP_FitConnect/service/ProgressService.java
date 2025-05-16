package com.example.OOP_FitConnect.service;

import com.example.OOP_FitConnect.model.Progress;
import java.util.List;

public interface ProgressService {
    List<Progress> getAllProgress();
    Progress getProgressById(String id);
    List<Progress> getProgressByUserId(String userId);
    Progress createProgress(Progress progress);
    Progress updateProgress(String id, Progress progress);
    boolean deleteProgress(String id);
}