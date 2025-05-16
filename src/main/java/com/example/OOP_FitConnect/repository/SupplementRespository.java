package com.example.OOP_FitConnect.repository;

import com.example.OOP_FitConnect.model.Supplement;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class SupplementRespository {

    private final List<Supplement> supplements = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public void SupplementRepository() {
        // Initialize with some sample data (replace with your actual data loading if needed)
        supplements.add(new Supplement(nextId.getAndIncrement(), "Whey Protein", "High-quality protein for muscle recovery.", 49.99, "Protein"));
        supplements.add(new Supplement(nextId.getAndIncrement(), "Creatine Monohydrate", "Enhances strength and power.", 29.99, "Performance"));
        supplements.add(new Supplement(nextId.getAndIncrement(), "BCAA Powder", "Helps reduce muscle soreness.", 34.99, "Recovery"));
    }

    public List<Supplement> findAll() {
        return new ArrayList<>(supplements); // Return a copy to prevent external modification
    }

    public Supplement findById(Long id) {
        return supplements.stream()
                .filter(supplement -> supplement.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Supplement save(Supplement supplement) {
        supplement.setId(nextId.getAndIncrement());
        supplements.add(supplement);
        return supplement;
    }

    // You can add more methods for updating, deleting, or filtering supplements
}
