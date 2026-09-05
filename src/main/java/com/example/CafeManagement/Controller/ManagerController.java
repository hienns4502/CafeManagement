package com.example.CafeManagement.Controller;

import com.example.CafeManagement.Customize.CustomUserDetails;
import com.example.CafeManagement.Entity.Employee;
import com.example.CafeManagement.Entity.Position;
import com.example.CafeManagement.Service.EmployeeService;
import com.example.CafeManagement.Service.PositionService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Slf4j
@Data
@RequestMapping("/manager")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@org.springframework.stereotype.Controller
public class ManagerController {

    PositionService positionService;

    EmployeeService employeeService;

    @GetMapping("/employees/add")
    public String showSignUpForm(Model model) {
        List<Position> positions = positionService.getAllPositions();
        model.addAttribute("user", new Employee());
        model.addAttribute("positions", positions);
        model.addAttribute("activeParent", "nhanVien");
        model.addAttribute("activeChild", "nhanVien-add");
        return "employee/addEmployee";
    }

    @PostMapping("/employees/process_add")
    public String processSignUpForm(
            Employee employee,
            @RequestParam("positionId") String positionId,
            @RequestParam(value = "avatarFile", required = false) MultipartFile file,
            RedirectAttributes redirectAttributes
    ) throws IOException {
        employeeService.createEmployee(employee, positionId, file);
        redirectAttributes.addFlashAttribute("successMessage", "Employee created successfully");
        return "redirect:/manager/employees";
    }

    @GetMapping("/employees")
    public String listEmployee(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("listEmployees", employees);
        model.addAttribute("activeParent", "nhanVien");
        model.addAttribute("activeChild", "nhanVien-list");
        return "employee/employees";
    }

    @GetMapping("/employees/delete/{id}")
    public String deleteEmployee(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        employeeService.deleteEmployee(id);
        redirectAttributes.addFlashAttribute("successMessage", "Employee deleted successfully");
        return "redirect:/manager/employees";
    }

    @GetMapping("/employees/edit/{id}")
    public String showEditForm(@PathVariable(value = "id") String id, Model model, Authentication authentication) {
        Employee employee = employeeService.getEmployee(id);
        List<Position> positions = positionService.getAllPositions();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("employee", employee);
        model.addAttribute("positions", positions);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("title", "Chỉnh sửa nhân viên");
        return "employee/editEmployee";
    }

    @PostMapping("/employees/edit/process_edit")
    public String prosessEdit(
            @ModelAttribute Employee employee,
            @RequestParam(value = "positionId") String positionId,
            @RequestParam(value = "avatarFile", required = false) MultipartFile file,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) throws IOException {
        employeeService.updateEmployee(employee, positionId, file);
        redirectAttributes.addFlashAttribute("successMessage", "Updated successfully");
        return "redirect:/manager/employees";
    }

    @PostMapping("/employees/search")
    public String searchEmployee(@RequestParam(value = "keyword") String keyword, Model model) {
        List<Employee> employees = employeeService.searchEmployees(keyword);
        model.addAttribute("listEmployees", employees);
        model.addAttribute("key", keyword);
        model.addAttribute("activeParent", "nhanVien");
        model.addAttribute("activeChild", "nhanVien-list");
        return "employee/employees";
    }

}










