package es.codeurjc.daw.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import es.codeurjc.daw.controller.dto.DepartmentDto;
import es.codeurjc.daw.entity.Department;

@Mapper
public interface DepartmentMapper {

    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

    DepartmentDto departmentToDto(Department department);
    Department dtoToDepartment(DepartmentDto departmentDto);
}
