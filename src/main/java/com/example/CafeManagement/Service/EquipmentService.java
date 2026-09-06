package com.example.CafeManagement.Service;

import com.example.CafeManagement.Entity.Equipment;
import com.example.CafeManagement.Repository.EquipmentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class EquipmentService {
    EquipmentRepository equipmentRepository;

    public List<Equipment> getEquipmentList() {
        return equipmentRepository.findAll();
    }

    public void createEquipment(Equipment equipment) {
        equipmentRepository.save(equipment);
    }

    public void deleteEquipment(String equipmentId) {
        Equipment equipment = equipmentRepository.findById(equipmentId).orElseThrow(() -> new RuntimeException("Equipment not found"));
        equipmentRepository.delete(equipment);
    }

    public Equipment getEquipment(String equipmentId) {
        Equipment foundEquipment = equipmentRepository.findById(equipmentId).orElseThrow(() -> new RuntimeException("Equipment not found"));
        return foundEquipment;
    }

    public void updateEquipment(Equipment equipment) {
        String id = equipment.getId();
        Equipment foundEquipment = equipmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Equipment not found"));
        equipmentRepository.save(equipment);
    }

    public List<Equipment> getEquipmentByName(String key) {
        List<Equipment> list =  equipmentRepository.findByTenThietBiContainingIgnoreCase(key);
        return list;
    }
}
