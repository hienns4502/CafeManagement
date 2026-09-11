package com.example.CafeManagement.Repository;

import com.example.CafeManagement.Entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoucherRepository extends JpaRepository<Voucher, String> {
    List<Voucher> findByTenKhuyenMaiContainingIgnoreCase(String tenKhuyenMai);
}
