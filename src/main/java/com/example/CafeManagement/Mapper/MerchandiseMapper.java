package com.example.CafeManagement.Mapper;

import com.example.CafeManagement.DTO.MerchandiseResponse;
import com.example.CafeManagement.Entity.Merchandise;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring" , nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MerchandiseMapper {
    MerchandiseResponse toMerchadiseResponse(Merchandise merchandise);
    Merchandise updateMerchandise(@MappingTarget Merchandise merchandiseTarger, Merchandise merchandise);

}
