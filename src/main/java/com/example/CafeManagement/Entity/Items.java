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
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column(nullable = false, unique = true, columnDefinition = "NVARCHAR(30)")
    String tenMon;
    Long giaTien;
    @OneToOne(mappedBy = "mon", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    Ingredient thanhPhan;

}
