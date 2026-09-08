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
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    int soLuong;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "items_id", nullable = false, updatable = false)
    Items mon;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "unit_id")
    Unit donViTinh;
}
