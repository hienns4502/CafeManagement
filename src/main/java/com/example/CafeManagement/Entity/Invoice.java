package com.example.CafeManagement.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String invoiceId;
    String maBan;
    String maNhanVien;
    String maKhuyenMai;
    Long khachDua;
    Long thoiLai;
    String trangThai;
}
