package com.example.CafeManagement.Mapper;

import com.example.CafeManagement.DTO.EmployeeCreateRequest;
import com.example.CafeManagement.DTO.EmployeeUpdateRequest;
import com.example.CafeManagement.DTO.EmployeeUpdateRequestUser;
import com.example.CafeManagement.DTO.UpdateProfile;
import com.example.CafeManagement.Entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeMapper {
    Employee toEmployee(EmployeeCreateRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password" , ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "pathAvatar", ignore = true)
    void updateEmployee(EmployeeUpdateRequest employeeUpdate, @MappingTarget Employee employee);

    void updateEmployeeUser(EmployeeUpdateRequestUser employeeUpdate, @MappingTarget Employee employee);


    @Mapping(target = "pathAvatar", ignore = true)
    void updateProfile(@MappingTarget Employee employee,  UpdateProfile updateProfile);
}
