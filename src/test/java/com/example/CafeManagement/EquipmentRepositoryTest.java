package com.example.CafeManagement;

import com.example.CafeManagement.Entity.Equipment;
import com.example.CafeManagement.Repository.EquipmentRepository;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class EquipmentRepositoryTest {
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private TestEntityManager testEntityManager;
    @Test
    public void testCreateEquipment()
    {
        Equipment equipment = Equipment.builder()
                .donGia(12000000l)
                .ngayMua(LocalDate.now())
                .soLuong(2)
                .tenThietBi("Máy pha cà phê")
                .build();
        Equipment saveEquipment = equipmentRepository.save(equipment);
        Equipment foundEquipment = testEntityManager.find(Equipment.class,equipment.getId());
        assertThat(foundEquipment.getId()).isEqualTo(saveEquipment.getId());
    }
}
