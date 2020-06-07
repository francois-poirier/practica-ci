package es.codeurjc.daw.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import es.codeurjc.daw.controller.dto.EmployeeDto;
import es.codeurjc.daw.entity.Employee;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDto employeeToDto(Employee employee);
    Employee dtoToEmployee(EmployeeDto employeeDto);
}
