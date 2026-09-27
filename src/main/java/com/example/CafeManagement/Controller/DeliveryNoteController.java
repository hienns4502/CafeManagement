package com.example.CafeManagement.Controller;

import com.example.CafeManagement.DTO.DeliveryNoteDTO;
import com.example.CafeManagement.DTO.DocumentSearchCriteria;
import com.example.CafeManagement.Entity.DeliveryNote;
import com.example.CafeManagement.Entity.Employee;
import com.example.CafeManagement.Entity.GoodsReceipt;
import com.example.CafeManagement.Entity.Merchandise;
import com.example.CafeManagement.Repository.MerchandiseRepository;
import com.example.CafeManagement.Service.DeliveryNoteFacade;
import com.example.CafeManagement.Service.DeliveryNoteService;
import com.example.CafeManagement.Service.EmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Controller
@RequestMapping("manager/delivery-note")
public class DeliveryNoteController {

    EmployeeService employeeService;
    MerchandiseRepository  merchandiseRepository;
    DeliveryNoteService deliveryNoteService;
    DeliveryNoteFacade deliveryNoteFacade;

    @GetMapping("/create")
    public String create(Model model) {
        List<Employee>  employees = employeeService.getAllEmployees();
        List<Merchandise> merchandises = merchandiseRepository.findAll();
        DeliveryNoteDTO  deliveryNoteDTO = new DeliveryNoteDTO();
        model.addAttribute("deliveryNoteDTO", deliveryNoteDTO);
        model.addAttribute("employees", employees);
        model.addAttribute("merchandises", merchandises);
        model.addAttribute("activeParent", "khoHang");
        model.addAttribute("activeChild", "hangHoa-export");
        model.addAttribute("title", "Xuất hàng");
        return "delivery_note/deliveryNote";
    }

    @PostMapping("/process-create")
    public String processCreate(DeliveryNoteDTO deliveryNoteDTO) {
        deliveryNoteFacade.createDeliveryNote(deliveryNoteDTO);
        return "redirect:/manager/delivery-note/create";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<DeliveryNote> deliveryNoteList = deliveryNoteService.getDeliveryNoteList();
        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
        model.addAttribute("deliveryNoteList", deliveryNoteList);
        model.addAttribute("criteria", criteria);
        model.addAttribute("activeParent", "khoHang");
        model.addAttribute("activeChild", "hangHoa-export-list");
        model.addAttribute("title", "Danh sách phiếu xuất hàng");
        return "delivery_note/deliveryNoteList";
    }

    @GetMapping("/search")
    public String search(@ModelAttribute DocumentSearchCriteria criteria, Model model) {
        List<DeliveryNote> deliveryNoteList= deliveryNoteService.searchDeliveryNoteLists(criteria);
        model.addAttribute("deliveryNoteList", deliveryNoteList);
        model.addAttribute("criteria", criteria);
        model.addAttribute("activeParent", "khoHang");
        model.addAttribute("activeChild", "hangHoa-export-list");
        model.addAttribute("title", "Danh sách phiếu xuất hàng");
        return "delivery_note/deliveryNoteList";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable String id) {
        DeliveryNote deliveryNote = deliveryNoteService.getDeliveryNoteById(id);
        model.addAttribute("deliveryNote", deliveryNote);
        model.addAttribute("activeParent", "khoHang");
        model.addAttribute("activeChild", "hangHoa-export-list");

        return "delivery_note/deliveryNoteDetail";
    }
}
