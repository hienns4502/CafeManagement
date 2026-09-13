package com.example.CafeManagement.Service;

import com.example.CafeManagement.Customize.CustomUserDetails;
import com.example.CafeManagement.DTO.GoodsReceiptDTO;
import com.example.CafeManagement.DTO.GoodsReceiptDetailDTO;
import com.example.CafeManagement.Entity.Employee;
import com.example.CafeManagement.Entity.GoodsReceipt;
import com.example.CafeManagement.Entity.GoodsReceiptMerchandiseDetail;
import com.example.CafeManagement.Entity.Merchandise;
import com.example.CafeManagement.Mapper.GoodsReceiptMapper;
import com.example.CafeManagement.Mapper.GoodsReceiptMerchandiseDetailMapper;
import com.example.CafeManagement.Repository.EmployeeRepository;
import com.example.CafeManagement.Repository.GoodsReceiptRepository;
import com.example.CafeManagement.Repository.MerchandiseRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class GoodsReceiptService {
    GoodsReceiptRepository  goodsReceiptRepository;
    EmployeeRepository employeeRepository;
    GoodsReceiptMapper goodsReceiptMapper;
    GoodsReceiptMerchandiseDetailMapper  goodsReceiptMerchandiseDetailMapper;
    MerchandiseRepository merchandiseRepository;

    public void processCreate(GoodsReceiptDTO goodsReceiptDTO) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = customUserDetails.getId();
        Employee nguoiNhap = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        GoodsReceipt goodsReceipt = goodsReceiptMapper.toGoodsReceipt(goodsReceiptDTO);
        goodsReceipt.setNguoiNhap(nguoiNhap);

        long tongTien = goodsReceiptDTO.getGoodsReceiptDetailDTOS().stream()
                .mapToLong(detailDTO -> (long) detailDTO.getSoLuong() * detailDTO.getDonGia())
                .sum();
        goodsReceipt.setTongTien(tongTien);

        Set<GoodsReceiptMerchandiseDetail> goodsReceiptDetailList = goodsReceiptDTO.getGoodsReceiptDetailDTOS()
                .stream().map(detailDTO -> {
                    GoodsReceiptMerchandiseDetail detail = new GoodsReceiptMerchandiseDetail();
                    detail.setSoLuong(detailDTO.getSoLuong());
                    detail.setDonGia(detailDTO.getDonGia());

                    detail.setPhieuNhap(goodsReceipt);

                    if (detailDTO.getHangHoa() != null && detailDTO.getHangHoa().getId() != null) {
                        Merchandise merchandise = merchandiseRepository.findById(detailDTO.getHangHoa().getId())
                                .orElseThrow(() -> new RuntimeException("Merchandise not found"));
                        detail.setHangHoa(merchandise);
                    }

                    return detail;
                }).collect(Collectors.toSet());

        goodsReceipt.setGoodsReceiptMerchandiseDetails(goodsReceiptDetailList);
        goodsReceiptRepository.save(goodsReceipt);
        goodsReceipt.getGoodsReceiptMerchandiseDetails().forEach(detail -> {
            String merchandiseId = detail.getHangHoa().getId();
            Merchandise merchandise = merchandiseRepository.findById(merchandiseId).orElseThrow(() -> new RuntimeException("Merchandise not found"));
            merchandise.setSoLuong(merchandise.getSoLuong() + detail.getSoLuong());
            merchandiseRepository.save(merchandise);
        });
    }
}
