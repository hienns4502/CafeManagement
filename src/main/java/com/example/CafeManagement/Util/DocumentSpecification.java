package com.example.CafeManagement.Util;

import com.example.CafeManagement.DTO.DocumentSearchCriteria;
import com.example.CafeManagement.Entity.DeliveryNote;
import com.example.CafeManagement.Entity.GoodsReceipt;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DocumentSpecification {

    public static <T> Specification<T> filterByCriteria(DocumentSearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Class<?> entityClass = root.getModel().getJavaType();

            // 1. Lọc theo từ khóa (Keyword)
            if (criteria.getKeyword() != null && !criteria.getKeyword().trim().isEmpty()) {
                String pattern = "%" + criteria.getKeyword().trim().toLowerCase() + "%";

                if (entityClass.equals(GoodsReceipt.class)) {
                    // GoodsReceipt: Tìm theo tên người nhập hoặc tên nhà cung cấp
                    Predicate searchByNguoiNhap = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("nguoiNhap").get("hoTen")), pattern
                    );
                    Predicate searchByNhaCungCap = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("nhaCungCap").get("tenNhaCungCap")), pattern
                    );
                    predicates.add(criteriaBuilder.or(searchByNguoiNhap, searchByNhaCungCap));

                } else if (entityClass.equals(DeliveryNote.class)) {
                    // DeliveryNote: Tìm theo tên người xuất hoặc tên người nhận
                    Predicate searchByNguoiXuat = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("nguoiXuat").get("hoTen")), pattern
                    );
                    Predicate searchByNguoiNhan = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("nguoiNhan").get("hoTen")), pattern
                    );
                    predicates.add(criteriaBuilder.or(searchByNguoiXuat, searchByNguoiNhan));
                }
            }

            // 2. Lọc theo khoảng thời gian (ngayNhap cho GoodsReceipt, ngayXuat cho DeliveryNote)
            String dateFieldName = entityClass.equals(GoodsReceipt.class) ? "ngayNhap" : "ngayXuat";

            log.info("----------------------dateFieldName:{}",dateFieldName);

            if (criteria.getStartDate() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(dateFieldName), criteria.getStartDate()));
            }

            if (criteria.getEndDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(dateFieldName), criteria.getEndDate()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}