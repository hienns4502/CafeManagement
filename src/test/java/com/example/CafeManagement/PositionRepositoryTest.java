package com.example.CafeManagement;

import com.example.CafeManagement.Entity.Position;
import com.example.CafeManagement.Entity.Role;
import com.example.CafeManagement.Repository.EmployeeRepository;
import com.example.CafeManagement.Repository.PositionRepository;
import com.example.CafeManagement.Repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)

public class PositionRepositoryTest {
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testCreatePosition(){
        Position position = new Position();
        position.setChucVu("Waiter");
        position.setLuong(7000000);
        Position savedPosition =  positionRepository.save(position);
        Position foundPosition = testEntityManager.find(Position.class, savedPosition.getId());

        assertThat(foundPosition.getId()).isEqualTo(savedPosition.getId());
    }
    @Test
    public void testFindByUserName(){
        String username = "lananh";
        employeeRepository.findByUsername(username);
        assertThat(employeeRepository.findByUsername(username)).isNotNull();
    }

    @Test
    public void addRoleToPosition(){
        // 1. Tạo và lưu Role trước để có ID
        Role role = roleRepository.findByName("ROLE_USER");

        // 2. Lấy Position từ database (hoặc tạo mới Position nếu chưa có)
        Position position = positionRepository.findByChucVu("Counter");

        // 3. Gán danh sách role vào position
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        position.setRoles(roles);

        // 4. QUAN TRỌNG: Phải gọi save() để Hibernate đẩy câu lệnh insert xuống bảng trung gian position_role
        positionRepository.save(position);

        // 5. Kiểm tra kết quả
        Position updatedPosition = positionRepository.findByChucVu("Counter");
        assertThat(updatedPosition).isNotNull();
        assertThat(updatedPosition.getRoles()).isNotEmpty();
    }
}
