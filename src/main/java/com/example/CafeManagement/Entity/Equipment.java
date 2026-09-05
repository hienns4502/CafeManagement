package com.example.CafeManagement.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String tenThietBi;
    @Column(nullable = false)
    int soLuong = 0;
    LocalDate ngayMua;
    Long donGia;
}
