package com.example.CafeManagement.DTO;

import com.example.CafeManagement.Entity.GoodsReceipt;
import com.example.CafeManagement.Entity.Merchandise;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GoodsReceiptDetailDTO {
    int soLuong;
    Long donGia;
    Merchandise  hangHoa;
}
