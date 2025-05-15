package com.example.OOP_FitConnect.service;

import com.example.OOP_FitConnect.model.Supplement;
import com.example.OOP_FitConnect.repository.SupplementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplementService {

    private final SupplementRepository supplementRepository;

    @Autowired
    public SupplementService(SupplementRepository supplementRepository) {
        this.supplementRepository = supplementRepository;
    }

    public List<Supplement> getAllSupplements() {
        return supplementRepository.findAll();
    }

    public Supplement getSupplementById(Long id) {
        return supplementRepository.findById(id);
    }

    public Supplement addSupplement(Supplement supplement) {
        // You can add business logic here, e.g., validation
        return supplementRepository.save(supplement);
    }

    // You can add more service methods for updating, deleting, or other business rules
}
