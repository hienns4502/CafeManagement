package com.example.CafeManagement.Controller;

import com.example.CafeManagement.Entity.Employee;
import com.example.CafeManagement.Entity.Position;
import com.example.CafeManagement.Service.EmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@org.springframework.stereotype.Controller
public class Controller {

    EmployeeService employeeService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("activeParent", "trangChu");
        return "index";
    }

    @GetMapping("/403")
    public String show403Page() {
        return "403";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Employee employee = employeeService.getMyInfo();
        model.addAttribute("employee", employee);
        model.addAttribute("activeParent", "trangCaNhan");
        model.addAttribute("title", "Trang cá nhân");
        return "profile";
    }

    @GetMapping("/profile-edit")
    public String editProfile(Model model) {
        Employee employee = employeeService.getMyInfo();
        model.addAttribute("employee", employee);
        model.addAttribute("title","Chỉnh sửa thông tin cá nhân");
        return "editEmployee";
    }

    @PostMapping("/profile-edit/process")
    public String prosessEditProfile(
            @ModelAttribute Employee employee,
            @RequestParam(value = "positionId", required = false) String positionId,
            @RequestParam(value = "avatarFile", required = false) MultipartFile file,
            RedirectAttributes redirectAttributes
    ) throws IOException {
        employeeService.updateEmployee(employee, positionId, file);
        redirectAttributes.addFlashAttribute("successMessage", "Updated successfully");
        return "redirect:/profile";
    }
}
