package com.example.CafeManagement.Controller;

import com.example.CafeManagement.DTO.GoodsReceiptDTO;
import com.example.CafeManagement.DTO.DocumentSearchCriteria;
import com.example.CafeManagement.Entity.GoodsReceipt;
import com.example.CafeManagement.Service.GoodsReceiptService;
import com.example.CafeManagement.Service.MerchandiseService;
import com.example.CafeManagement.Service.SupplierService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("manager/goods-receipt")
@Controller
public class GoodsReceiptController {
    GoodsReceiptService goodsReceiptService;
    MerchandiseService merchandiseService;
    SupplierService supplierService;

    @GetMapping("/create")
    public String createNewGoodsReceipt(Model model) {
        GoodsReceiptDTO goodsReceiptDTO = new GoodsReceiptDTO();

        model.addAttribute("goodsReceiptDTO", goodsReceiptDTO);
        model.addAttribute("nhaCungCap", supplierService.getSupplierList());
        model.addAttribute("hangHoa", merchandiseService.getMerchandiseList());
        model.addAttribute("activeParent", "khoHang");
        model.addAttribute("activeChild", "hangHoa-import");
        model.addAttribute("title", "Nhập hàng");
        return "goodsreceipt/goodsReceipt";
    }

    @PostMapping("/process-create")
    public String processCreate(GoodsReceiptDTO goodsReceiptDTO) {
        goodsReceiptService.processCreate(goodsReceiptDTO);
        return "redirect:/manager/goods-receipt/create";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<GoodsReceipt> goodsReceiptList= goodsReceiptService.getGoodListsReceipt();
        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
        model.addAttribute("goodsReceipt", goodsReceiptList);
        model.addAttribute("criteria", criteria);
        model.addAttribute("activeParent", "khoHang");
        model.addAttribute("activeChild", "hangHoa-import-list");
        model.addAttribute("title", "Danh sách phiếu nhập hàng");
        return "goodsreceipt/goodsReceiptList";
    }

    @GetMapping("/search")
    public String search(@ModelAttribute DocumentSearchCriteria criteria, Model model) {
        List<GoodsReceipt> goodsReceiptList= goodsReceiptService.searchGoodLists(criteria);
        model.addAttribute("goodsReceipt", goodsReceiptList);
        model.addAttribute("criteria", criteria);
        model.addAttribute("activeParent", "khoHang");
        model.addAttribute("activeChild", "hangHoa-import-list");
        model.addAttribute("title", "Danh sách phiếu nhập hàng");
        return "goodsreceipt/goodsReceiptList";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable String id) {
        GoodsReceipt goodsReceipt = goodsReceiptService.getGoodsReceiptById(id);
        model.addAttribute("goodsReceipt", goodsReceipt);
        model.addAttribute("activeParent", "khoHang");
        model.addAttribute("activeChild", "hangHoa-import-list");

        return "goodsreceipt/goodsReceiptDetail";
    }
}

