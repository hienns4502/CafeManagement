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
public class Merchandise {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String tenHangHoa;
    String soLuong;
    Long donGia;
    @ManyToOne(fetch = FetchType.LAZY)
    Unit donViTinh;

}
