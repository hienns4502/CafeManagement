package com.example.CafeManagement.Service;

import com.example.CafeManagement.Entity.Employee;
import com.example.CafeManagement.Entity.Position;
import com.example.CafeManagement.Repository.EmployeeRepository;
import com.example.CafeManagement.Repository.PositionRepository;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class EmployeeService {
    EmployeeRepository employeeRepository;
    PositionRepository positionRepository;

    public void createEmployee(Employee employee, String positionId, MultipartFile file) throws IOException {
        // mã hóa mk
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        //lưu file đã upload vào local storage
        // kiểm tra xem người dùng có upload file ảnh lên không
        // nếu có mới lưu
        Path filePath = null;
        if (file != null && !file.isEmpty()) {
            Path folder = Paths.get("F:\\OnTapSQL\\BaiLam\\QuanLyQuanCafe\\uploads");
            String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String fileName = Objects.isNull(fileExtension)
                    ? UUID.randomUUID().toString()
                    : UUID.randomUUID().toString() + "." + fileExtension;
            filePath = folder.resolve(fileName).normalize().toAbsolutePath();
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        if (filePath != null) {
            employee.setPathAvatar(filePath.toString());
        }
        Position position = positionRepository.findById(positionId).orElseThrow(() -> new RuntimeException("Position Not Found"));
        employee.setPosition(position);

        employeeRepository.save(employee);

    }

    public void deleteEmployee(String employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee Not Found"));
            employeeRepository.delete(employee);
    }
}
