package com.example.CafeManagement.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

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
    @Column(nullable = false, columnDefinition = "NVARCHAR(50)")
    String tenThietBi;
    @Column(nullable = false)
    int soLuong = 0;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate ngayMua;
    Long donGia;
}
