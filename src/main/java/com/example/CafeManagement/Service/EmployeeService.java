package com.example.CafeManagement.Service;

import com.example.CafeManagement.Customize.CustomUserDetails;
import com.example.CafeManagement.Entity.Employee;
import com.example.CafeManagement.Entity.Position;
import com.example.CafeManagement.Mapper.EmployeeMapper;
import com.example.CafeManagement.Repository.EmployeeRepository;
import com.example.CafeManagement.Repository.PositionRepository;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@EnableMethodSecurity
@Getter
@Setter
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class EmployeeService {
    EmployeeRepository employeeRepository;
    PositionRepository positionRepository;
    EmployeeMapper employeeMapper;

    public Employee getEmployee(String id) {
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee Not Found"));
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public void createEmployee(Employee employee, String positionId, MultipartFile file) throws IOException {
        // mã hóa mk
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        //lưu file đã upload vào local storage
        // kiểm tra xem người dùng có upload file ảnh lên không
        // nếu có mới lưu
        if (file != null && !file.isEmpty()) {
            Path folder = Paths.get("F:\\OnTapSQL\\BaiLam\\QuanLyQuanCafe\\uploads");
            String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String fileName = Objects.isNull(fileExtension)
                    ? UUID.randomUUID().toString()
                    : UUID.randomUUID().toString() + "." + fileExtension;
            Path filePath = folder.resolve(fileName).normalize().toAbsolutePath();
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            employee.setPathAvatar(fileName);
        }
        Position position = positionRepository.findById(positionId).orElseThrow(() -> new RuntimeException("Position Not Found"));
        employee.setPosition(position);

        employeeRepository.save(employee);

    }

    public void deleteEmployee(String employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee Not Found"));
        employeeRepository.delete(employee);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or #employee.getId() == principal.id")
    public void updateEmployee(@Param("employee") Employee employee, String positionId, MultipartFile file) throws IOException {
        String employeeId = employee.getId();
        Employee foundEmployee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee Not Found"));
        employeeMapper.updateEmployee(employee, foundEmployee);
        if (file != null && !file.isEmpty()) {
            Path path = Paths.get("F:\\OnTapSQL\\BaiLam\\QuanLyQuanCafe\\uploads");
            String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String fileName = Objects.isNull(fileExtension) ? UUID.randomUUID().toString()
                    : UUID.randomUUID().toString() + "." + fileExtension;
            Path filePath = path.resolve(fileName).normalize().toAbsolutePath();
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            foundEmployee.setPathAvatar(fileName);
        }
//        if (positionId != null) {
//            Position position = positionRepository.findById(positionId).orElseThrow(() -> new RuntimeException("Position Not Found"));
//            foundEmployee.setPosition(position);
//        }
        if (positionId != null) {
            boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                    .stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ADMIN"));

            if (isAdmin) {
                Position position = positionRepository.findById(positionId)
                        .orElseThrow(() -> new RuntimeException("Position Not Found"));
                foundEmployee.setPosition(position);
            }
        }
        employeeRepository.save(foundEmployee);
    }

    public Employee getMyInfo() {
        var contexxt = SecurityContextHolder.getContext();
        String name = contexxt.getAuthentication().getName();
        Employee employee = employeeRepository.findByUsername(name);
        return employee;
    }


}
