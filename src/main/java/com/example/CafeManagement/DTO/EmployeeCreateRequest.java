package com.example.CafeManagement.DTO;

import com.example.CafeManagement.Entity.Position;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreateRequest {

    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(min = 1, max = 50, message = "Tên đăng nhập phải từ 6 đến 50 ký tự ")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Tên đăng nhập chỉ chứa chữ, số, dấu gạch dưới")
    String username;
    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, max = 30, message = "Mật khẩu phải có ít nhất 6 ký tự")
//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{6,}$", message = "Mật khẩu phải chứa chữ hoa, chữ thường, số và ký tự đặc biệt")
    String password;
    @NotBlank(message = "Họ tên không được để trống")
    @Size(max = 30, message = "Họ tên không quá 30 ký tự")
    String hoTen;
    @NotBlank(message = "Địa chỉ không được bỏ trống")
    @Size(max = 255, message = "Địa chỉ không được quá 255 ký tự")
    String diaChi;
    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(0[3|5|7|8|9])+([0-9]{8})$", message = "Số điện thoại không đúng định dạng (phải gồm 10 số và bắt đầu bằng đầu số VN hợp lệ)")
    String soDienThoai;

}
