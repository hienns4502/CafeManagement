package com.example.CafeManagement.Mapper;

import com.example.CafeManagement.DTO.UpdateProfile;
import com.example.CafeManagement.Entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password" , ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "pathAvatar", ignore = true)
    void updateEmployee(Employee employee,  @MappingTarget Employee employeeUpdate);

    @Mapping(target = "pathAvatar", ignore = true)
    void updateProfile(@MappingTarget Employee employee,  UpdateProfile updateProfile);
}
