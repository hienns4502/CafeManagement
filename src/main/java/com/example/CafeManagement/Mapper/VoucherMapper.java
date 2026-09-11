package com.example.CafeManagement.Mapper;

import com.example.CafeManagement.DTO.VoucherResponse;
import com.example.CafeManagement.Entity.Voucher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoucherMapper {
    VoucherResponse toVoucherResponse(Voucher voucher);
}
