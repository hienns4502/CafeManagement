package com.example.CafeManagement.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column(nullable = false, unique = true, length = 50)
    String username;
    @Column(nullable = false, length = 64)
    String password;
    @Column(nullable = false, columnDefinition = "NVARCHAR(30)")
    String hoTen;
    @Column(nullable = false, columnDefinition = "NVARCHAR(255)")
    String diaChi;
    @Column(nullable = false, columnDefinition = "VARCHAR(10)")
    String soDienThoai;
    String pathAvatar;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "position_id", nullable = false)
    Position position;

}
