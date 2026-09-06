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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

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
        model.addAttribute("activeParent", "thietBi");
        model.addAttribute("activeChild", "thietBi-add");
        model.addAttribute("title", "Thêm thiết bị");
        return "equipment/equipmentAdd";
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

}
