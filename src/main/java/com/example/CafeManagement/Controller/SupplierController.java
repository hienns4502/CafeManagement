package com.example.CafeManagement.Controller;

import com.example.CafeManagement.DTO.EquipmentResponse;
import com.example.CafeManagement.Entity.Equipment;
import com.example.CafeManagement.Entity.Supplier;
import com.example.CafeManagement.Service.SupplierService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("manager/supplier")
@Controller
public class SupplierController {

    SupplierService supplierService;

    @GetMapping("/list")
    public String showSupplierList(Model model) {
        List<Supplier> supplierList = supplierService.getSupplierList();
        model.addAttribute("supplierList", supplierList);
        model.addAttribute("activeParent", "nhaCungCap");
        model.addAttribute("activeChild", "nhaCungCap-list");
        model.addAttribute("title", "Danh sách nhà cung cấp");
        return "supplier/supplierList";
    }

    @GetMapping("/add")
    public String showFormAdd(Model model) {
        Supplier supplier = new Supplier();
        model.addAttribute("supplier", supplier);
        model.addAttribute("action", "add");
        model.addAttribute("activeParent", "nhaCungCap");
        model.addAttribute("activeChild", "nhaCungCap-add");
        model.addAttribute("title", "Thêm nhà cung cấp");
        return "supplier/supplierForm";
    }

    @PostMapping("/process-add")
    public String addSupplier(
            @ModelAttribute("supplier") Supplier supplier,
            RedirectAttributes redirectAttributes
    ) {
        supplierService.createSupplier(supplier);
        redirectAttributes.addFlashAttribute("successMessage", "Supplier created successfully");
        return "redirect:/manager/supplier/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteSupplier(@PathVariable("id") String supplierId, RedirectAttributes redirectAttributes) {
        supplierService.deleteSupplier(supplierId);
        redirectAttributes.addFlashAttribute("successMessage", "Supplier deleted successfully");
        return "redirect:/manager/supplier/list";
    }

    @GetMapping("/edit/{id}")
    public String showForm(@PathVariable("id") String supplierId, Model model) {
        Supplier supplier = supplierService.getSupplier(supplierId);
        model.addAttribute("supplier", supplier);
        model.addAttribute("action", "edit");
        model.addAttribute("title", "Cập nhật nhà cung cấp");
        return "supplier/supplierForm";
    }

    @PostMapping("process-update")
    public String updateSupplier(@ModelAttribute("supplier") Supplier supplier, RedirectAttributes redirectAttributes){
        supplierService.updateSupplier(supplier);
        redirectAttributes.addFlashAttribute("successMessage", "Supplier updated successfully");
        return "redirect:/manager/supplier/list";
    }

    @PostMapping("/search")
    public String searchEquipment(@RequestParam(value = "keyword") String key, Model  model){
        List<Supplier> supplierList = supplierService.getSupplierByName(key);
        model.addAttribute("key", key);
        model.addAttribute("supplierList", supplierList);
        model.addAttribute("activeParent", "nhaCungCap");
        model.addAttribute("activeChild", "nhaCungCap-list");
        model.addAttribute("title", "Danh sách nhà cung cấp");
        return   "supplier/supplierList";
    }

}
