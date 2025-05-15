package com.example.OOP_FitConnect.controller;

import com.example.OOP_FitConnect.model.Equipment;
import com.example.OOP_FitConnect.model.User;
import com.example.OOP_FitConnect.service.EquipmentService;
import com.example.OOP_FitConnect.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminEquipmentController {

    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private UserService userService;

    @GetMapping("/equipment")
    public String equipmentPage(HttpServletRequest request, Model model) {
        String userId = (String) request.getSession().getAttribute("userId");
        User admin = userService.getUserById(userId);
        if (admin != null && admin.isAdmin()) {
            List<Equipment> equipmentList = equipmentService.getAll();
            model.addAttribute("admin", admin);
            model.addAttribute("equipmentList", equipmentList);
            return "Admin_equipments";
        }
        return "redirect:/login";
    }

    @PostMapping("/api/add-equipment")
    @ResponseBody
    public Map<String, Object> addEquipment(@RequestParam String name, @RequestParam String description, @RequestParam int quantity) {
        Map<String, Object> response = new HashMap<>();
        Equipment equipment = new Equipment();
        equipment.setName(name);
        equipment.setDescription(description);
        equipment.setQuantity(quantity);
        equipmentService.add(equipment);
        response.put("success", true);
        return response;
    }

    @PostMapping("/api/update-equipment/{equipmentId}")
    @ResponseBody
    public Map<String, Object> updateEquipment(@PathVariable String equipmentId, @RequestParam String name, @RequestParam String description, @RequestParam int quantity) {
        Map<String, Object> response = new HashMap<>();
        Equipment equipment = equipmentService.getById(equipmentId);
        if (equipment != null) {
            equipment.setName(name);
            equipment.setDescription(description);
            equipment.setQuantity(quantity);
            equipmentService.add(equipment);
            response.put("success", true);
        } else {
            response.put("success", false);
            response.put("message", "Equipment not found");
        }
        return response;
    }

    @PostMapping("/api/delete-equipment/{equipmentId}")
    @ResponseBody
    public Map<String, Object> deleteEquipment(@PathVariable String equipmentId) {
        Map<String, Object> response = new HashMap<>();
        equipmentService.delete(equipmentId);
        response.put("success", true);
        return response;
    }
}

@RestController
@RequestMapping("/api/equipment")
class EquipmentApiController {
    @Autowired
    private EquipmentService service;

    @GetMapping
    public List<Equipment> getAll() { return service.getAll(); }

    @PostMapping
    public Equipment add(@RequestBody Equipment eq) {
        service.add(eq);
        return eq;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}