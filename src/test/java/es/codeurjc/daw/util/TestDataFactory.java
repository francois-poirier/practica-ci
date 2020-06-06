package es.codeurjc.daw.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import es.codeurjc.daw.controller.dto.DepartmentDto;
import es.codeurjc.daw.entity.Department;

public class TestDataFactory {

    public static Department getSingleDepartment(Long id){
        return Department.builder()
                .id(id)
                .departmentName("Department name " + id)
                .build();
    }

    public static List<Department> getDepartmentList(Long departmentsCount){
        return LongStream.rangeClosed(1, departmentsCount)
                .mapToObj(TestDataFactory::getSingleDepartment)
                .collect(Collectors.toList());
    }

    public static List<DepartmentDto> getDepartmentListDTO(Long departmentsCount) {
        return LongStream.rangeClosed(1, departmentsCount)
                .mapToObj(TestDataFactory::getSingleDepartmentDto)
                .collect(Collectors.toList());
    }

    public static DepartmentDto getSingleDepartmentDto(Long id){
        return DepartmentDto.builder()
                .departmentName("Department name " + id)
                .build();
    }


}
