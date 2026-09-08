package com.example.CafeManagement.DTO;

import com.example.CafeManagement.Entity.Unit;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MerchandiseResponse {
    String id;
    String tenHangHoa;
    int soLuong;
    Long donGia;
    Long tongTien;
    Unit donViTinh;
}
