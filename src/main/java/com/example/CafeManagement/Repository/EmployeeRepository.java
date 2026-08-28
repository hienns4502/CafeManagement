package com.example.CafeManagement.Repository;

import com.example.CafeManagement.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
