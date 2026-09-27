package com.example.CafeManagement.DTO;

import com.example.CafeManagement.Entity.DeliveryNoteDetail;
import com.example.CafeManagement.Entity.Employee;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeliveryNoteDTO {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime ngayXuat;
    String lyDo;
    Long tongTien = 0l;
    Employee nguoiXuat;
    Employee nguoiNhan;
    List<DeliveryNoteDetail> deliveryNoteDetails;
}
