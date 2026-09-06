package com.example.CafeManagement.Repository;

import com.example.CafeManagement.Entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, String> {
    List<Equipment>findByTenThietBiContainingIgnoreCase (String key);
}
