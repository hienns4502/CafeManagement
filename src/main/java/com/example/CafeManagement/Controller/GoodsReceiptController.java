package com.example.CafeManagement.Controller;

import com.example.CafeManagement.Customize.CustomUserDetails;
import com.example.CafeManagement.DTO.GoodsReceiptDTO;
import com.example.CafeManagement.Entity.GoodsReceipt;
import com.example.CafeManagement.Service.GoodsReceiptService;
import com.example.CafeManagement.Service.MerchandiseService;
import com.example.CafeManagement.Service.SupplierService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("manager/goods-receipt")
@Controller
public class GoodsReceiptController {
    GoodsReceiptService goodsReceiptService;
    MerchandiseService  merchandiseService;
    SupplierService supplierService;

    @GetMapping("/create")
    public String createNewGoodsReceipt(Model model) {
        GoodsReceiptDTO goodsReceiptDTO = new GoodsReceiptDTO();

        model.addAttribute("title", "Nhập hàng");
        model.addAttribute("goodsReceiptDTO", goodsReceiptDTO);
        model.addAttribute("nhaCungCap", supplierService.getSupplierList());
        model.addAttribute("hangHoa", merchandiseService.getMerchandiseList());
        return "goodsreceipt/goodsReceipt";
    }

    @PostMapping("/process-create")
    public String processCreate(GoodsReceiptDTO goodsReceiptDTO) {
        goodsReceiptService.processCreate(goodsReceiptDTO);
        return "redirect:/manager/goods-receipt/create";
    }



}
