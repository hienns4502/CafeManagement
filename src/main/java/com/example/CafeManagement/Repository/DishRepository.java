package com.example.CafeManagement.Repository;

import com.example.CafeManagement.Entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, String> {
    List<Dish> findByTenMonContainingIgnoreCase(String tenMon);
}
