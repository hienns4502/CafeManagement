package com.example.CafeManagement.Mapper;

import com.example.CafeManagement.DTO.GoodsReceiptDTO;
import com.example.CafeManagement.Entity.GoodsReceipt;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GoodsReceiptMapper {
    GoodsReceipt toGoodsReceipt(GoodsReceiptDTO goodsReceiptDTO);
}
