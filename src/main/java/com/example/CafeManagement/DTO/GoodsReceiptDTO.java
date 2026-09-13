package com.example.CafeManagement.DTO;

import com.example.CafeManagement.Entity.Employee;
import com.example.CafeManagement.Entity.GoodsReceiptMerchandiseDetail;
import com.example.CafeManagement.Entity.Supplier;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GoodsReceiptDTO {
    LocalDateTime ngayNhap;
    List<GoodsReceiptDetailDTO> goodsReceiptDetailDTOS;
    Supplier nhaCungCap;
}
