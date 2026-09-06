package com.example.CafeManagement.Controller;

import com.example.CafeManagement.DTO.EquipmentResponse;
import com.example.CafeManagement.Entity.Employee;
import com.example.CafeManagement.Entity.Equipment;
import com.example.CafeManagement.Mapper.EquipmentMapper;
import com.example.CafeManagement.Repository.EquipmentRepository;
import com.example.CafeManagement.Service.EquipmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/manager/equipment")
@Controller
public class EquipmentController {
    EquipmentService equipmentService;
    EquipmentMapper equipmentMapper;

    @GetMapping("/list")
    public String showEquipmentList(Model  model)
    {
        List<EquipmentResponse> equipmentResponseList = equipmentService.getEquipmentList()
                .stream().map(equipmentMapper::toEquipmentResponse).toList();
        equipmentResponseList.forEach(e -> {
            Long tong = e.getDonGia() * e.getSoLuong();
            e.setTongTien(tong);
        });
        model.addAttribute("equipmentResponseList", equipmentResponseList);
        model.addAttribute("activeParent", "thietBi");
        model.addAttribute("activeChild", "thietBi-list");
        model.addAttribute("title", "Danh sách thiết bị");
        return "equipment/equipmentList";
    }

    @GetMapping("/add")
    public String showFormAdd(Model  model){
        model.addAttribute("equipment", new Equipment());
        model.addAttribute("action", "add");
        model.addAttribute("activeParent", "thietBi");
        model.addAttribute("activeChild", "thietBi-add");
        model.addAttribute("title", "Thêm thiết bị");
        return "equipment/equipmentForm";
    }

    @PostMapping("/process-add")
    public String addEquipment(
            @ModelAttribute("equipment") Equipment equipment,
            RedirectAttributes redirectAttributes
    ){
        equipmentService.createEquipment(equipment);
        redirectAttributes.addFlashAttribute("successMessage", "Equipment created successfully");
        return "redirect:/manager/equipment/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteEquipment(@PathVariable("id") String equipmentId, RedirectAttributes redirectAttributes){
        equipmentService.deleteEquipment(equipmentId);
        redirectAttributes.addFlashAttribute("successMessage", "Equipment deleted successfully");
        return "redirect:/manager/equipment/list";
    }

    @GetMapping("/edit/{id}")
    public String showForm(@PathVariable("id") String equipmentId, Model  model){
        Equipment equipment = equipmentService.getEquipment(equipmentId);
        model.addAttribute("equipment", equipment);
        model.addAttribute("action", "edit");
        model.addAttribute("title", "Cập nhật thiết bị");
        return "equipment/equipmentForm";
    }

    @PostMapping("process-update")
    public String updateEquipment(@ModelAttribute("equipment") Equipment equipment, RedirectAttributes redirectAttributes){
        equipmentService.updateEquipment(equipment);
        redirectAttributes.addFlashAttribute("successMessage", "Equipment updated successfully");
        return "redirect:/manager/equipment/list";
    }

    @PostMapping("/search")
    public String searchEquipment(@RequestParam(value = "keyword") String key, Model  model){
        List<EquipmentResponse> equipmentResponseList = equipmentService.getEquipmentByName(key)
                .stream().map(equipmentMapper::toEquipmentResponse).toList();
        equipmentResponseList.forEach(e -> {
            Long tong = e.getDonGia() * e.getSoLuong();
            e.setTongTien(tong);
        });
        model.addAttribute("equipmentResponseList", equipmentResponseList);
        model.addAttribute("activeParent", "thietBi");
        model.addAttribute("key", key);
        model.addAttribute("activeChild", "thietBi-list");
        model.addAttribute("title", "Danh sách thiết bị");
        return   "equipment/equipmentList";
    }
}
