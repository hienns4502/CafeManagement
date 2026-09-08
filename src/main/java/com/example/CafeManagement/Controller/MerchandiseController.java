package com.example.CafeManagement.Controller;

import com.example.CafeManagement.DTO.MerchandiseResponse;
import com.example.CafeManagement.Entity.Merchandise;
import com.example.CafeManagement.Entity.Unit;
import com.example.CafeManagement.Service.MerchandiseService;
import com.example.CafeManagement.Service.UnitService;
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
@Controller
@RequestMapping("manager/merchandise")
public class MerchandiseController {
    MerchandiseService merchandiseService;
    UnitService unitService;

    @GetMapping("/list")
    public String showMerchandiseList(Model model) {
        List<MerchandiseResponse> merchandiseResponseList = merchandiseService.getMerchandiseList();
        model.addAttribute("merchandiseResponseList", merchandiseResponseList);
        model.addAttribute("activeParent", "khoHang");
        model.addAttribute("activeChild", "hangHoa-list");
        model.addAttribute("title", "Danh sách hàng hóa");
        return "/merchandise/merchandiseList";
    }

    @GetMapping("/add")
    public String showMerchandiseForm(Model model) {
        Merchandise merchandise = new Merchandise();
        List<Unit> units = unitService.getUnitList();
        model.addAttribute("merchandise", merchandise);
        model.addAttribute("units", units);
        model.addAttribute("activeParent", "khoHang");
        model.addAttribute("activeChild", "hangHoa-add");
        model.addAttribute("title", "Thêm hàng hóa");
        model.addAttribute("action", "add");
        return "/merchandise/merchandiseForm";
    }

    @PostMapping("process-add")
    public String processAddMerchandise(
            @ModelAttribute("merchandise") Merchandise merchandise,
            @RequestParam("unitId") String unitId,
            RedirectAttributes redirectAttributes
    ) {
        merchandiseService.addMerchandise(merchandise, unitId);
        redirectAttributes.addFlashAttribute("successMessage", "Merchandise created successfully");
        return "redirect:/manager/merchandise/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteMerchandise(
            @PathVariable String id,
            RedirectAttributes redirectAttributes
    ){
        merchandiseService.deleteMerchandise(id);
        redirectAttributes.addFlashAttribute("successMessage", "Merchandise deleted successfully");
        return "redirect:/manager/merchandise/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditMerchandise(@PathVariable String id, Model model) {
        List<Unit> units = unitService.getUnitList();
        Merchandise foundMerchandise = merchandiseService.getMerchandise(id);
        model.addAttribute("merchandise", foundMerchandise);
        model.addAttribute("units", units);
        model.addAttribute("title", "Thêm hàng hóa");
        model.addAttribute("action", "edit");
        return "/merchandise/merchandiseForm";
    }

    @PostMapping("/process-update")
    public String updateMerchandise(
            @ModelAttribute("merchandise") Merchandise merchandise,
            @RequestParam("unitId")  String unitId,
            RedirectAttributes redirectAttributes
    ){
        merchandiseService.updateMerchandise(merchandise, unitId);
        redirectAttributes.addFlashAttribute("successMessage", "Merchandise updated successfully");
        return "redirect:/manager/merchandise/list";
    }

    @PostMapping("/search")
    public String searchMerchandise(@RequestParam("keyword") String key, Model model) {
        List<MerchandiseResponse> merchandiseResponseListSearch = merchandiseService.getMerchandiseListByKey(key);
        model.addAttribute("merchandiseResponseList", merchandiseResponseListSearch);
        model.addAttribute("activeParent", "khoHang");
        model.addAttribute("activeChild", "hangHoa-list");
        model.addAttribute("title", "Danh sách hàng hóa");
        model.addAttribute("key", key);
        return "/merchandise/merchandiseList";
    }
}
