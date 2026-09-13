package com.example.CafeManagement.Controller;

import com.example.CafeManagement.DTO.MerchandiseResponse;
import com.example.CafeManagement.DTO.VoucherResponse;
import com.example.CafeManagement.Entity.Merchandise;
import com.example.CafeManagement.Entity.Unit;
import com.example.CafeManagement.Entity.Voucher;
import com.example.CafeManagement.Service.VoucherService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)
@Controller
@RequestMapping("/manager/voucher")
public class VoucherController {

    VoucherService voucherService;

    @GetMapping("/list")
    public String showVoucherList(Model model) {
        List<Voucher> voucherList = voucherService.getVoucherList();
        model.addAttribute("voucherList", voucherList);
        model.addAttribute("activeParent", "marketing");
        model.addAttribute("activeChild", "marketing-list");
        model.addAttribute("title", "Danh sách khuyến mãi");
        return "/voucher/voucherList";
    }

    @GetMapping("/add")
    public String showMerchandiseForm(Model model) {
        Voucher voucher = new Voucher();
        model.addAttribute("voucher", voucher);
        model.addAttribute("activeParent", "marketing");
        model.addAttribute("activeChild", "marketing-add");
        model.addAttribute("title", "Thêm khuyến mãi");
        model.addAttribute("action", "add");
        return "/voucher/voucherForm";
    }

    @PostMapping("process-add")
    public String processAddMerchandise(
            @ModelAttribute("voucher") Voucher voucher,
            RedirectAttributes redirectAttributes
    ) {
        voucherService.addVoucher(voucher);
        redirectAttributes.addFlashAttribute("successMessage", "Voucher created successfully");
        return "redirect:/manager/voucher/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteMerchandise(
            @PathVariable String id,
            RedirectAttributes redirectAttributes
    ){
        voucherService.deleteVoucher(id);
        redirectAttributes.addFlashAttribute("successMessage", "Voucher deleted successfully");
        return "redirect:/manager/voucher/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditMerchandise(@PathVariable String id, Model model) {
        VoucherResponse voucherResponse = voucherService.getVoucher(id);
        model.addAttribute("voucher", voucherResponse);
        model.addAttribute("title", "Cập nhật khuyến mãi");
        model.addAttribute("action", "edit");
        return "/voucher/voucherForm";
    }

    @PostMapping("/process-update")
    public String updateMerchandise(
            @ModelAttribute("voucher") Voucher voucher,
            RedirectAttributes redirectAttributes
    ){
        voucherService.updateVouCher(voucher);
        redirectAttributes.addFlashAttribute("successMessage", "Voucher updated successfully");
        return "redirect:/manager/voucher/list";
    }

    @PostMapping("/search")
    public String searchMerchandise(@RequestParam("keyword") String key, Model model) {
        List<Voucher> voucherList = voucherService.getVoucherListByKey(key);
        model.addAttribute("voucherList", voucherList);
        model.addAttribute("activeParent", "marketing");
        model.addAttribute("activeChild", "marketing-list");
        model.addAttribute("title", "Danh sách khuyến mãi");
        model.addAttribute("key", key);
        return "/voucher/voucherList";
    }
}
