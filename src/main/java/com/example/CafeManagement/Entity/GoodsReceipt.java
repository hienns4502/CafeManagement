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
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    LocalDateTime ngayNhap;
    Long tongTien;
    @ManyToOne(fetch = FetchType.LAZY)
    Employee nguoiNhap;
    @OneToMany(mappedBy = "phieuNhap", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<GoodsReceiptMerchandiseDetail> goodsReceiptMerchandiseDetails;
    @ManyToOne
    @JoinColumn(name = "suplier_id")
    Supplier nhaCungCap;
}
