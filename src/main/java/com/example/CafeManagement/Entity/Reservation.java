package com.example.CafeManagement.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String tenKhachHang;
    String soDienThoai;
    LocalDateTime thoiGianBatDau;
    LocalDateTime thoiGianKetThuc;
    String trangThai;
    String ghiChu;
    @ManyToOne(fetch = FetchType.LAZY)
    Employee employee;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id", nullable = false)
    Tables tables;
}
