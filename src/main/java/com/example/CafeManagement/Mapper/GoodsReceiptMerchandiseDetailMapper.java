package com.example.CafeManagement.Mapper;

import com.example.CafeManagement.DTO.GoodsReceiptDTO;
import com.example.CafeManagement.DTO.GoodsReceiptDetailDTO;
import com.example.CafeManagement.Entity.GoodsReceiptMerchandiseDetail;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GoodsReceiptMerchandiseDetailMapper {
    GoodsReceiptMerchandiseDetail toGoodsReceiptMerchandiseDetail(GoodsReceiptDetailDTO goodsReceiptDetailDTO);
}
