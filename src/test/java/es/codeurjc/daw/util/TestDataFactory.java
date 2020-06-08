package es.codeurjc.daw.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import es.codeurjc.daw.controller.dto.DepartmentDto;
import es.codeurjc.daw.controller.dto.EmployeeDto;
import es.codeurjc.daw.controller.dto.JobDto;
import es.codeurjc.daw.entity.Department;
import es.codeurjc.daw.entity.Employee;
import es.codeurjc.daw.entity.Job;

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
    
    public static Job getSingleJob(Long id){
        return Job.builder()
                .id(id)
                .jobTitle("Job title " + id)
                .maxSalary(2000*id)
                .minSalary(1000*id)
                .build();
    }

    public static List<Job> getJobList(Long jobsCount) {
    	 return LongStream.rangeClosed(1, jobsCount)
                 .mapToObj(TestDataFactory::getSingleJob)
                 .collect(Collectors.toList());
    }
    
    public static List<JobDto> getJobListDTO(Long jobsCount) {
        return LongStream.rangeClosed(1, jobsCount)
                .mapToObj(TestDataFactory::getSingleJobDto)
                .collect(Collectors.toList());
    }

    public static JobDto getSingleJobDto(Long id){
        return JobDto.builder()
                .jobTitle("Job title " + id)
                .maxSalary(2000*id)
                .minSalary(1000*id)
                .build();
    }

}
