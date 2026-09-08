package com.example.CafeManagement;

import com.example.CafeManagement.Entity.Equipment;
import com.example.CafeManagement.Entity.Merchandise;
import com.example.CafeManagement.Entity.Unit;
import com.example.CafeManagement.Repository.MerchandiseRepository;
import com.example.CafeManagement.Repository.UnitRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class MerchandiseRepositoryTest {
    @Autowired
    private MerchandiseRepository merchandiseRepository;
    @Autowired
    private UnitRepository  unitRepository;
    @Autowired
    private TestEntityManager testEntityManager;
    @Test
    void createMerchandiseTest() throws Exception {


        Unit unit1 = Unit.builder()
                .tenDonViTinh("Túi")
                .build();
        Unit savedUnit1 = unitRepository.save(unit1);


//        Merchandise merchandise = Merchandise.builder()
//                .tenHangHoa("Bột mattcha D'bird")
//                .donViTinh(savedUnit)
//                .donGia(124000l)
//                .soLuong(4)
//                .build();
//        Merchandise savedMerchandise = merchandiseRepository.save(merchandise);
//        Merchandise foungMerchandise = testEntityManager.find(Merchandise.class, savedMerchandise.getId());
//        assertThat(foungMerchandise.getId()).isEqualTo(savedMerchandise.getId());
    }
}
