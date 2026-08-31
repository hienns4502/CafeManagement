package com.example.CafeManagement.Controller;

import com.example.CafeManagement.Entity.Employee;
import com.example.CafeManagement.Entity.Position;
import com.example.CafeManagement.Repository.EmployeeRepository;
import com.example.CafeManagement.Repository.PositionRepository;
import com.example.CafeManagement.Service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @Autowired
    EmployeeService employeeService;

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
    public String processSignUpForm(
            Employee employee,
            @RequestParam("positionId") String positionId,
            @RequestParam(value = "avatarFile", required = false) MultipartFile file,
            RedirectAttributes redirectAttributes
    ) throws IOException {
        employeeService.createEmployee(employee, positionId, file);
        redirectAttributes.addFlashAttribute("successMessage", "Employee created successfully");
        return  "redirect:/employees";
    }

    @GetMapping("/employees")
    public String listEmployee(Model model) {
        List<Employee> employees = employeeRepository.findAll();
        model.addAttribute("listEmployees", employees);
        return "employees";
    }

    @GetMapping("/employees/delete/{id}")
    public String deleteEmployee(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        employeeService.deleteEmployee(id);
        redirectAttributes.addFlashAttribute("successMessage", "Employee deleted successfully");
        return "redirect:/employees";
    }
}
