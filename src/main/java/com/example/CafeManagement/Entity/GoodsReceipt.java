package com.example.CafeManagement.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class GoodsReceipt {
    @Id
    String id;
    LocalDateTime ngayNhap;
    long tongTien;
    @Column(nullable = false
    )
    String matHang;
    @ManyToOne(fetch = FetchType.LAZY)
    Employee employee;
    @OneToMany(mappedBy = "goodsReceipt", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<GoodsReceiptEquipmentDetails> goodsReceiptEquipmentDetails;
}
