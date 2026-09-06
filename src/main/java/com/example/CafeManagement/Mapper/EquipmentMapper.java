package com.example.CafeManagement.Mapper;

import com.example.CafeManagement.DTO.EquipmentResponse;
import com.example.CafeManagement.Entity.Equipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface EquipmentMapper {
     EquipmentResponse toEquipmentResponse(Equipment equipment);
}
