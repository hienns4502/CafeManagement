package com.example.CafeManagement.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class GoodsReceiptEquipmentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    int soLuong;
    Long donGia;
    @ManyToOne(fetch = FetchType.LAZY)
            @JoinColumn(name = "equipment_id", nullable = false)
    Equipment equipment;
    @ManyToOne(fetch = FetchType.LAZY)
            @JoinColumn(name = "goodsreceipt_id",  nullable = false)
    GoodsReceipt goodsReceipt;
}
