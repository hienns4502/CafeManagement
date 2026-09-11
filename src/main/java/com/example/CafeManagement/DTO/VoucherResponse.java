package com.example.CafeManagement.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoucherResponse {
    String id;
    String tenKhuyenMai;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate ngayBatDau;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate ngayKetThuc;
    int phanTramGiamGia;
}
