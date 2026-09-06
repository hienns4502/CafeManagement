package com.example.CafeManagement.DTO;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EquipmentResponse {
    String id;
    String tenThietBi;
    int soLuong = 0;
    LocalDate ngayMua;
    Long donGia;
    Long tongTien;
}
