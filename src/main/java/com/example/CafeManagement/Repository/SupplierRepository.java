package com.example.CafeManagement.Repository;

import com.example.CafeManagement.Entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String> {
    List<Supplier> findByTenNhaCungCapContainingIgnoreCase(String keyword);
}
