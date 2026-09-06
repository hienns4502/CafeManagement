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
public class DeliveryNoteDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    int soLuong;
    @ManyToOne(fetch = FetchType.LAZY)
            @JoinColumn(name = "hang_hoa_id", nullable = false)
    Merchandise hangHoa;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phieu_xuat_id", nullable = false)
    DeliveryNote phieuXuat;
}
