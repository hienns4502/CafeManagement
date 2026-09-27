package com.example.CafeManagement.Service;

import com.example.CafeManagement.DTO.DeliveryNoteDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeliveryNoteFacade {
    DeliveryNoteService deliveryNoteService;

    @Retryable(
            retryFor = OptimisticLockingFailureException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 100)
    )
    public void createDeliveryNote(DeliveryNoteDTO dto) {
        deliveryNoteService.processCreate(dto);
    }

    @Recover
    public void recover(OptimisticLockingFailureException e, DeliveryNoteDTO dto) {
        throw new RuntimeException("Không thể xử lý phiếu xuất do tranh chấp dữ liệu sau nhiều lần thử", e);
    }
}
