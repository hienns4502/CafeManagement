package com.example.CafeManagement.Mapper;

import com.example.CafeManagement.DTO.DishFormDTO;
import com.example.CafeManagement.Entity.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DishMapper {
    Dish toDish(DishFormDTO dishFormDTO);
    DishFormDTO toDishForm(Dish dish);
}
