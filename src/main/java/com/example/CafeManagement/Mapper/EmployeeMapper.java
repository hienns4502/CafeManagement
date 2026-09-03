package com.example.CafeManagement.Mapper;

import com.example.CafeManagement.Entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password" , ignore = true)
    @Mapping(target = "position", ignore = true)
    void updateEmployee(Employee employee,  @MappingTarget Employee employeeUpdate);
}
