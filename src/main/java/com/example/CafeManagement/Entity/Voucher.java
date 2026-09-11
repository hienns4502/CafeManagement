package com.example.CafeManagement.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column(nullable = false, columnDefinition = "NVARCHAR(255)")
    String tenKhuyenMai;
    LocalDate ngayBatDau;
    LocalDate ngayKetThuc;
    int phanTramGiamGia;
}




















