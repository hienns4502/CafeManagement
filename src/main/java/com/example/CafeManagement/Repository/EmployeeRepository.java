package com.example.CafeManagement.Repository;

import com.example.CafeManagement.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Employee findByUsername(String username);
    @Query("SELECT e FROM Employee e JOIN FETCH e.position")
    List<Employee> findAllWithChucVu();
}
