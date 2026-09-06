package com.example.CafeManagement.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class DeliveryNote {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    LocalDateTime ngayXuat;
    @ManyToOne(fetch = FetchType.LAZY)
            @JoinColumn(name = "nguoi_xuat_id", nullable = false)
    Employee nguoiXuat;
    @ManyToOne(fetch = FetchType.LAZY)
            @JoinColumn(name = "nguoi_nhan_id", nullable = false)
    Employee nguoiNhan;
    @OneToMany(mappedBy = "phieuXuat", cascade = CascadeType.ALL, orphanRemoval = true)
    List<DeliveryNoteDetail> deliveryNoteDetails;
}
