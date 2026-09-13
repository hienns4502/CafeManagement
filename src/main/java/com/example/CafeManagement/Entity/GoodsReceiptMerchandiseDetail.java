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
public class GoodsReceiptMerchandiseDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    int soLuong;
    Long donGia;
    @ManyToOne(fetch = FetchType.LAZY)
            @JoinColumn(name = "merchandise_id", nullable = false)
    Merchandise hangHoa;
    @ManyToOne(fetch = FetchType.LAZY)
            @JoinColumn(name = "goodsreceipt_id",  nullable = false)
    GoodsReceipt phieuNhap;
}
