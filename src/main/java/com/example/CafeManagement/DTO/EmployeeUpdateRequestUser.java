package com.example.CafeManagement.DTO;

import com.example.CafeManagement.Entity.Position;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUpdateRequestUser {
    String id;
    @NotBlank(message = "Họ tên không được để trống")
    @Size(max = 30, message = "Họ tên không quá 30 ký tự")
    String hoTen;
    @NotBlank(message = "Địa chỉ không được bỏ trống")
    @Size(max = 255, message = "Địa chỉ không được quá 255 ký tự")
    String diaChi;
    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(0[3|5|7|8|9])+([0-9]{8})$", message = "Số điện thoại không đúng định dạng (phải gồm 10 số và bắt đầu bằng đầu số VN hợp lệ)")
    String soDienThoai;
    String pathAvatar;
}
