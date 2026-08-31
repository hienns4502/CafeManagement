package com.example.CafeManagement.Repository;

import com.example.CafeManagement.Entity.Employee;
import com.example.CafeManagement.Entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, String> {
    Position findByChucVu(String chucVu);
}
