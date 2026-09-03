package com.example.CafeManagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProfile {
    private String hoTen;
    private String diaChi;
    private String soDienThoai;
    private String pathAvatar;
}
