package com.example.CafeManagement.Repository;

import com.example.CafeManagement.Entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, String> {
}
