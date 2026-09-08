package com.example.CafeManagement.Service;

import com.example.CafeManagement.DTO.MerchandiseResponse;
import com.example.CafeManagement.Entity.Merchandise;
import com.example.CafeManagement.Entity.Unit;
import com.example.CafeManagement.Mapper.MerchandiseMapper;
import com.example.CafeManagement.Repository.MerchandiseRepository;
import com.example.CafeManagement.Repository.UnitRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class MerchandiseService {
    MerchandiseRepository merchandiseRepository;
    UnitRepository unitRepository;
    MerchandiseMapper merchandiseMapper;

    public List<MerchandiseResponse> getMerchandiseList() {
        List<MerchandiseResponse> merchandiseResponses = merchandiseRepository.findAll()
                .stream().map(merchandiseMapper::toMerchadiseResponse).toList();
        merchandiseResponses.forEach(m -> {
            Long tong = m.getDonGia() * m.getSoLuong();
            m.setTongTien(tong);
        });
        return merchandiseResponses;
    }

    public void addMerchandise(Merchandise merchandise, String unitId) {
        Unit foundUnit = unitRepository.findById(unitId).orElseThrow(() -> new RuntimeException("Unit Not Found"));
        merchandise.setDonViTinh(foundUnit);
        merchandiseRepository.save(merchandise);
    }

    public void deleteMerchandise(String merchandiseId) {
        Merchandise foundMerchandise = merchandiseRepository.findById(merchandiseId).orElseThrow(() -> new RuntimeException("Merchandise Not Found"));
        merchandiseRepository.delete(foundMerchandise);
    }

    public Merchandise getMerchandise(String merchandiseId) {
        Merchandise foundMerchandise = merchandiseRepository.findById(merchandiseId).orElseThrow(() -> new RuntimeException("Merchandise Not Found"));
        return foundMerchandise;
    }

    public void updateMerchandise(Merchandise merchandise, String unitId) {
        log.info("Updating merchandise {}", merchandise);
        Unit foundUnit = unitRepository.findById(unitId).orElseThrow(() -> new RuntimeException("Unit Not Found"));
        merchandise.setDonViTinh(foundUnit);
        String merchandiseId = merchandise.getId();
        Merchandise foundMerchandise = merchandiseRepository.findById(merchandiseId).orElseThrow(() -> new RuntimeException("Merchandise Not Found"));
        merchandiseRepository.save(merchandiseMapper.updateMerchandise(foundMerchandise, merchandise));
    }

    public List<MerchandiseResponse> getMerchandiseListByKey(String key) {
        List<MerchandiseResponse> merchandiseResponses = merchandiseRepository
                .findByTenHangHoaContainingIgnoreCase(key).stream()
                .map(merchandiseMapper :: toMerchadiseResponse).toList();
        merchandiseResponses.forEach(m -> {
            Long tong = m.getDonGia() * m.getSoLuong();
            m.setTongTien(tong);
        });
        return merchandiseResponses;
    }
}
