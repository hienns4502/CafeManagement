package com.example.CafeManagement.Service;

import com.example.CafeManagement.DTO.DeliveryNoteDTO;
import com.example.CafeManagement.DTO.DocumentSearchCriteria;
import com.example.CafeManagement.Entity.*;
import com.example.CafeManagement.Repository.DeliveryNoteRepository;
import com.example.CafeManagement.Repository.EmployeeRepository;
import com.example.CafeManagement.Repository.MerchandiseRepository;
import com.example.CafeManagement.Util.DocumentSpecification;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class DeliveryNoteService {

    EmployeeRepository employeeRepository;
    MerchandiseRepository merchandiseRepository;
    DeliveryNoteRepository deliveryNoteRepository;

    @Transactional
    public void processCreate(DeliveryNoteDTO deliveryNoteDTO) {

        String idNguoiXuat = deliveryNoteDTO.getNguoiXuat().getId();
        String idNguoiNhan = deliveryNoteDTO.getNguoiNhan().getId();

        Employee nguoiXuat = employeeRepository.findById(idNguoiXuat)
                .orElseThrow(() -> new RuntimeException("Employee (nguoiXuat) not found"));
        Employee nguoiNhan = employeeRepository.findById(idNguoiNhan)
                .orElseThrow(() -> new RuntimeException("Employee (nguoiNhan) not found"));

        DeliveryNote deliveryNote = new DeliveryNote();
        deliveryNote.setNguoiXuat(nguoiXuat);
        deliveryNote.setNguoiNhan(nguoiNhan);
        deliveryNote.setNgayXuat(deliveryNoteDTO.getNgayXuat());

        List<DeliveryNoteDetail> deliveryNoteDetailList = deliveryNoteDTO.getDeliveryNoteDetails()
                .stream()
                .map(detailDTO -> {
                    if (detailDTO.getHangHoa() == null || detailDTO.getHangHoa().getId() == null) {
                        throw new RuntimeException("Merchandise id is required for each detail");
                    }

                    Merchandise merchandise = merchandiseRepository.findById(detailDTO.getHangHoa().getId())
                            .orElseThrow(() -> new RuntimeException("Merchandise not found: " + detailDTO.getHangHoa().getId()));

                    DeliveryNoteDetail detail = new DeliveryNoteDetail();
                    detail.setSoLuong(detailDTO.getSoLuong());
                    detail.setHangHoa(merchandise);
                    detail.setPhieuXuat(deliveryNote);
                    return detail;
                })
                .toList();

        // Tính tổng tiền dựa trên dữ liệu Merchandise thật (đã load ở trên), và SET vào deliveryNote
        long tongTien = deliveryNoteDetailList.stream()
                .mapToLong(detail -> (long) detail.getSoLuong() * detail.getHangHoa().getDonGia())
                .sum();
        deliveryNote.setTongTien(tongTien);

        deliveryNote.setDeliveryNoteDetails(deliveryNoteDetailList);

        for (DeliveryNoteDetail detail : deliveryNoteDetailList) {
            Merchandise merchandise = detail.getHangHoa();

            int soLuongMoi = merchandise.getSoLuong() - detail.getSoLuong();
            if (soLuongMoi < 0) {
                throw new RuntimeException("Không đủ số lượng hàng: " + merchandise.getId()
                        + " (tồn: " + merchandise.getSoLuong() + ", cần: " + detail.getSoLuong() + ")");
            }
            merchandise.setSoLuong(soLuongMoi);

            merchandiseRepository.save(merchandise);
        }

        deliveryNoteRepository.save(deliveryNote);
    }

    public List<DeliveryNote> getDeliveryNoteList() {
        return deliveryNoteRepository.findAll();
    }

    public List<DeliveryNote> searchDeliveryNoteLists(DocumentSearchCriteria criteria) {
        Specification<DeliveryNote> spec = DocumentSpecification.filterByCriteria(criteria);
        return deliveryNoteRepository.findAll(spec);
    }

    public DeliveryNote getDeliveryNoteById(String id) {
        DeliveryNote foundDelivery = deliveryNoteRepository.findById(id).orElseThrow(() -> new RuntimeException("Delivery note not found: " + id));
        return foundDelivery;
    }
}
