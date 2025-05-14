package com.example.OOP_FitConnect.repository;

import com.example.OOP_FitConnect.model.Equipment;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class EquipmentRepository {
    private final Map<String, Equipment> equipmentMap = new HashMap<>();

    public List<Equipment> getAll() {
        return new ArrayList<>(equipmentMap.values());
    }

    public Equipment getById(String id) {
        return equipmentMap.get(id);
    }

    public void save(Equipment equipment) {
        equipmentMap.put(equipment.getId(), equipment);
    }

    public void delete(String id) {
        equipmentMap.remove(id);
    }
}