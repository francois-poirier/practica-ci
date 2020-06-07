package es.codeurjc.daw.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import es.codeurjc.daw.controller.dto.DepartmentDto;
import es.codeurjc.daw.controller.dto.EmployeeDto;
import es.codeurjc.daw.entity.Department;
import es.codeurjc.daw.entity.Employee;

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

    public static Employee getSingleEmployee(Long id){
        return Employee.builder()
                .id(id)
                .firstName("First name " + id)
                .lastName("Last name " + id)
                .salary(1000*id)
                .build();
    }

    public static List<Employee> getEmployeeList(Long employeesCount){
        return LongStream.rangeClosed(1, employeesCount)
                .mapToObj(TestDataFactory::getSingleEmployee)
                .collect(Collectors.toList());
    }

    public static List<EmployeeDto> getEmployeeListDTO(Long employeesCount) {
        return LongStream.rangeClosed(1, employeesCount)
                .mapToObj(TestDataFactory::getSingleEmployeeDto)
                .collect(Collectors.toList());
    }

    public static EmployeeDto getSingleEmployeeDto(Long id){
        return EmployeeDto.builder()
                .firstName("First name " + id)
                .lastName("Last name " + id)
                .salary(1000*id)
                .build();
    }

}
