package com.example.CafeManagement.Service;

import com.example.CafeManagement.Entity.Equipment;
import com.example.CafeManagement.Repository.EquipmentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class EquipmentService {
    EquipmentRepository equipmentRepository;
    public List<Equipment> getEquipmentList()
    {
        return equipmentRepository.findAll();
    }

    public void createEquipment(Equipment equipment)
    {
        equipmentRepository.save(equipment);
    }
}
