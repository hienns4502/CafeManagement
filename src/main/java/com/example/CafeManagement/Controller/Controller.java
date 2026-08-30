package com.example.CafeManagement.Controller;

import com.example.CafeManagement.Entity.Employee;
import com.example.CafeManagement.Entity.Position;
import com.example.CafeManagement.Repository.EmployeeRepository;
import com.example.CafeManagement.Repository.PositionRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    PositionRepository positionRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public String showSignUpForm(Model model) {
        List<Position> positions = positionRepository.findAll();
        model.addAttribute("user", new Employee());
        model.addAttribute("positions", positions);
        return "signup_form";
    }

    @PostMapping("/process_register")
    @ResponseBody
    public String processSignUpForm(
            Employee employee,
            @RequestParam("positionId") String positionId,
            @RequestParam(value = "avatarFile", required = false) MultipartFile file
    ) throws IOException {
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

        // In ra màn hình trình duyệt để xem
        return "Đăng ký thành công! Employee: " + employee.toString() + " | Position ID: " + positionId
                + "    file " + filePath;
    }

    @GetMapping("/employees")
    public String listEmployee(Model model) {
        List<Employee> employees = employeeRepository.findAll();
        model.addAttribute("listEmployees", employees);
        return "employees";
    }
}
