package com.example.CafeManagement.Controller;

import com.example.CafeManagement.DTO.DishFormDTO;
import com.example.CafeManagement.DTO.MerchandiseResponse;
import com.example.CafeManagement.DTO.VoucherResponse;
import com.example.CafeManagement.Entity.*;
import com.example.CafeManagement.Service.DishService;
import com.example.CafeManagement.Service.MerchandiseService;
import com.example.CafeManagement.Service.UnitService;
import com.example.CafeManagement.Service.VoucherService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Controller
@RequestMapping("manager/dish")
public class DishController {

        MerchandiseService  merchandiseService;
        UnitService unitService;
        DishService dishService;


    @GetMapping("/list")
    public String showDishList(Model model) {
        List<Dish> dishList = dishService.getDishList();
        model.addAttribute("dishList", dishList);
        model.addAttribute("activeParent", "thucdon");
        model.addAttribute("activeChild", "thucdon-list");
        model.addAttribute("title", "Danh sách thực đơn");
        return "/dish/dishList";
    }

    @GetMapping("/add")
    public String showDishForm(Model model) {
        DishFormDTO dish =  new DishFormDTO();
        List<MerchandiseResponse> merchandises = merchandiseService.getMerchandiseList();
        List<Unit> units = unitService.getUnitList();
        model.addAttribute("units", units);
        model.addAttribute("dishForm", dish);
        model.addAttribute("merchandises", merchandises);
        model.addAttribute("activeParent", "thucDon");
        model.addAttribute("activeChild", "thucDon-add");
        model.addAttribute("title", "Thêm thực đơn");
        model.addAttribute("action", "add");
        return "/dish/testdishForm";
    }

    @PostMapping("process-add")
    public String processAddDish(
            @ModelAttribute("dishForm") DishFormDTO dishForm,
            RedirectAttributes redirectAttributes
    ) {
        dishService.addDish(dishForm);
        redirectAttributes.addFlashAttribute("successMessage", "Dish created successfully");
        return "redirect:/manager/dish/add";
    }

    @GetMapping("/delete/{id}")
    public String deleteDish(
            @PathVariable String id,
            RedirectAttributes redirectAttributes
    ){
        dishService.deleteDish(id);
        redirectAttributes.addFlashAttribute("successMessage", "Dish deleted successfully");
        return "redirect:/manager/dish/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditDish(@PathVariable String id, Model model) {
        Dish dish = dishService.getDish(id);
        List<MerchandiseResponse> merchandises = merchandiseService.getMerchandiseList();
        List<Unit> units = unitService.getUnitList();
        model.addAttribute("units", units);
        model.addAttribute("dishForm", dish);
        model.addAttribute("merchandises", merchandises);
        model.addAttribute("title", "Cập nhật thực đơn");
        model.addAttribute("action", "edit");
        return "/dish/testdishForm";
    }

    @PostMapping("/process-update")
    public String updateDish(
            @ModelAttribute("dishForm") DishFormDTO dishFormDTO,
            RedirectAttributes redirectAttributes
    ){
        dishService.updateDish(dishFormDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Merchandise updated successfully");
        return "redirect:/manager/voucher/list";
    }

//    @PostMapping("/search")
//    public String searchMerchandise(@RequestParam("keyword") String key, Model model) {
//        List<Voucher> voucherList = voucherService.getVoucherListByKey(key);
//        model.addAttribute("voucherList", voucherList);
//        model.addAttribute("activeParent", "marketing");
//        model.addAttribute("activeChild", "marketing-list");
//        model.addAttribute("title", "Danh sách khuyến mãi");
//        model.addAttribute("key", key);
//        return "/voucher/voucherList";
//    }

}
