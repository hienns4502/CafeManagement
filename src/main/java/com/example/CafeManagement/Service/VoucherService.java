package com.example.CafeManagement.Service;


import com.example.CafeManagement.DTO.MerchandiseResponse;
import com.example.CafeManagement.DTO.VoucherResponse;
import com.example.CafeManagement.Entity.Merchandise;
import com.example.CafeManagement.Entity.Unit;
import com.example.CafeManagement.Entity.Voucher;
import com.example.CafeManagement.Mapper.VoucherMapper;
import com.example.CafeManagement.Repository.VoucherRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class VoucherService {

    VoucherRepository  voucherRepository;
    VoucherMapper  voucherMapper;

    public List<Voucher> getVoucherList() {
        List<Voucher> voucherList = voucherRepository.findAll();
        return voucherList;
    }

    public void addVoucher(Voucher voucher) {
        voucherRepository.save(voucher);
    }

    public void deleteVoucher(String voucherId) {
      Voucher foundVoucher = voucherRepository.findById(voucherId).orElseThrow(()->new RuntimeException("voucher not found"));
      voucherRepository.delete(foundVoucher);
    }

    public VoucherResponse getVoucher(String voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(()->new RuntimeException("voucher not found"));
        return voucherMapper.toVoucherResponse(voucher);
    }

    public void updateVouCher(Voucher voucher) {
        String voucherId = voucher.getId();
        Voucher foundVoucher = voucherRepository.findById(voucherId).orElseThrow(()->new RuntimeException("voucher not found"));
        voucherRepository.save(voucher);
    }

    public List<Voucher> getVoucherListByKey(String key) {
       List<Voucher> voucherList = voucherRepository.findByTenKhuyenMaiContainingIgnoreCase(key);
        return voucherList;
    }
}
