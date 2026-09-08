package com.example.CafeManagement.Repository;

import com.example.CafeManagement.Entity.Merchandise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MerchandiseRepository extends JpaRepository<Merchandise,String> {
    List<Merchandise> findByTenHangHoaContainingIgnoreCase(String key);
}
