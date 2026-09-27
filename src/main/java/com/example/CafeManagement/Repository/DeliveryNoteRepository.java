package com.example.CafeManagement.Repository;

import com.example.CafeManagement.Entity.DeliveryNote;
import com.example.CafeManagement.Entity.GoodsReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DeliveryNoteRepository extends JpaRepository<DeliveryNote,String>, JpaSpecificationExecutor<DeliveryNote> {
}
