package com.example.CafeManagement.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column(nullable = false, columnDefinition = "NVARCHAR(255)")
    String tenNhaCungCap;
    @Column(nullable = false, columnDefinition = "NVARCHAR(255)")
    String diaChi;
    String soDienThoai;
    @OneToMany(mappedBy = "nhaCungCap")
    Set<GoodsReceipt> goodsReceipts = new HashSet<>();
}
