package com.example.CafeManagement;

import com.example.CafeManagement.Entity.Position;
import com.example.CafeManagement.Repository.PositionRepository;
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

public class PositionRepositoryTest {
    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testCreatePosition(){
        Position position = new Position();
        position.setChucVu("Counter");
        position.setLuong(8000000);
        Position savedPosition =  positionRepository.save(position);
        Position foundPosition = testEntityManager.find(Position.class, savedPosition.getId());

        assertThat(foundPosition.getId()).isEqualTo(savedPosition.getId());


    }
}
