package com.example.OOP_FitConnect.repository;

import com.example.OOP_FitConnect.model.ClassSession;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
public class ClassSessionRepository {
    private static final String CSV_FILE = "classes.csv";
    private final Map<String, ClassSession> classesById = new HashMap<>();

    public ClassSessionRepository() {
        loadFromCSV();
    }

    private void loadFromCSV() {
        File file = new File(CSV_FILE);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 4) {
                    ClassSession cs = new ClassSession();
                    cs.setId(parts[0]);
                    cs.setName(parts[1]);
                    cs.setInstructor(parts[2]);
                    cs.setSchedule(parts[3]);
                    classesById.put(cs.getId(), cs);
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void saveToCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_FILE))) {
            pw.println("id,name,instructor,schedule");
            for (ClassSession cs : classesById.values()) {
                pw.println(String.join(",", cs.getId(), cs.getName(), cs.getInstructor(), cs.getSchedule()));
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public List<ClassSession> getAllClasses() {
        return new ArrayList<>(classesById.values());
    }

    public void addClass(ClassSession cs) {
        classesById.put(cs.getId(), cs);
        saveToCSV();
    }

    public void deleteClass(String id) {
        classesById.remove(id);
        saveToCSV();
    }
}