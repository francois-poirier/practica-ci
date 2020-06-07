package es.codeurjc.daw.service;

import static es.codeurjc.daw.util.TestDataFactory.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.codeurjc.daw.controller.dto.EmployeeDto;
import es.codeurjc.daw.entity.Employee;
import es.codeurjc.daw.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests of EmployeeService class")
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    @DisplayName("Get an empty list of Employees")
    public void givenNoEmployees_whenFindAllEmployees_thenGetEmptyList() {
        //given
        when(employeeRepository.findAll())
                .thenReturn(Collections.emptyList());

        //when
        List<EmployeeDto> employeeList = employeeService.findAll();

        //then
        assertEquals(0, employeeList.size());
    }

    @Test
    @DisplayName("Get a list with single Employee")
    public void givenSingleEmployees_whenFindAllEmployees_thenSingleEmployeeList() {
        //given
        when(employeeRepository.findAll())
                .thenReturn(getEmployeeList(1L));

        //when
        List<EmployeeDto> employeeList = employeeService.findAll();

        //then
        assertEquals(1, employeeList.size());
        assertEquals("First name 1", employeeList.get(0).getFirstName());
    }

    @Test
    @DisplayName("Get a list of 500 Employees")
    public void given500Employees_whenFindAllEmployees_then500EmployeeList() {
        //given
        when(employeeRepository.findAll())
                .thenReturn(getEmployeeList(500L));

        //when
        List<EmployeeDto> employeeList = employeeService.findAll();

        //then
        assertEquals(500, employeeList.size());
    }

    @Test
    @DisplayName("Get a Employee by Id")
    public void givenSingleEmployee_whenFindById_thenGetSingleEmployee(){
        //given
        when(employeeRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getSingleEmployee(1L)));

        //when
        Optional<EmployeeDto> optEmployeeDto = employeeService.findById(1L);

        //then
        assertTrue(optEmployeeDto.isPresent());
        assertEquals("First name 1", optEmployeeDto.get().getFirstName());
    }

    @Test
    @DisplayName("Get a Employee by Id and return empty result")
    public void givenNoEmployee_whenFindById_thenGetEmptyOptional(){
        //given
        when(employeeRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        //when
        Optional<EmployeeDto> employeeDTOOpt = employeeService.findById(1L);

        //then
        assertFalse(employeeDTOOpt.isPresent());
    }

    @Test
    @DisplayName("Save a Employee")
    public void givenEmployee_whenSave_thenGetSavedEmployee() {
        //given
        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(getSingleEmployee(1L));

        EmployeeDto employeeDTO = getSingleEmployeeDto(1L);

        //when
        EmployeeDto savedEmployee = employeeService.save(employeeDTO);

        //then
        assertNotNull(savedEmployee.getId());
    }

    @Test
    @DisplayName("Update a Employee")
    public void givenSavedEmployee_whenUpdate_thenEmployeeIsUpdated() {
        //given
        when(employeeRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getSingleEmployee(1L)));

        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(getSingleEmployee(2L));

        es.codeurjc.daw.controller.dto.EmployeeDto toBeUpdatedEmployeeDto = getSingleEmployeeDto(2L);

        //when
        EmployeeDto updatedEmployeeDto = employeeService.update(1L, toBeUpdatedEmployeeDto);

        //then
        assertEquals(toBeUpdatedEmployeeDto.getFirstName(), updatedEmployeeDto.getFirstName());
        
    }
}
