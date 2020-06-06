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

import es.codeurjc.daw.controller.dto.DepartmentDto;
import es.codeurjc.daw.entity.Department;
import es.codeurjc.daw.repository.DepartmentRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests of DepartmentService class")
public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    @DisplayName("Get an empty list of Departments")
    public void givenNoDepartments_whenFindAllDepartments_thenGetEmptyList() {
        //given
        when(departmentRepository.findAll())
                .thenReturn(Collections.emptyList());

        //when
        List<DepartmentDto> departmentList = departmentService.findAll();

        //then
        assertEquals(0, departmentList.size());
    }

    @Test
    @DisplayName("Get a list with single Department")
    public void givenSingleDepartments_whenFindAllDepartments_thenSingleDepartmentList() {
        //given
        when(departmentRepository.findAll())
                .thenReturn(getDepartmentList(1L));

        //when
        List<DepartmentDto> departmentList = departmentService.findAll();

        //then
        assertEquals(1, departmentList.size());
        assertEquals("Department name 1", departmentList.get(0).getDepartmentName());
    }

    @Test
    @DisplayName("Get a list of 500 Departments")
    public void given500Departments_whenFindAllDepartments_then500DepartmentList() {
        //given
        when(departmentRepository.findAll())
                .thenReturn(getDepartmentList(500L));

        //when
        List<DepartmentDto> departmentList = departmentService.findAll();

        //then
        assertEquals(500, departmentList.size());
    }

    @Test
    @DisplayName("Get a Department by Id")
    public void givenSingleDepartment_whenFindById_thenGetSingleDepartment(){
        //given
        when(departmentRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getSingleDepartment(1L)));

        //when
        Optional<DepartmentDto> optDepartmentDto = departmentService.findById(1L);

        //then
        assertTrue(optDepartmentDto.isPresent());
        assertEquals("Department name 1", optDepartmentDto.get().getDepartmentName());
    }

    @Test
    @DisplayName("Get a Department by Id and return empty result")
    public void givenNoDepartment_whenFindById_thenGetEmptyOptional(){
        //given
        when(departmentRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        //when
        Optional<DepartmentDto> departmentDTOOpt = departmentService.findById(1L);

        //then
        assertFalse(departmentDTOOpt.isPresent());
    }

    @Test
    @DisplayName("Save a Department")
    public void givenDepartment_whenSave_thenGetSavedDepartment() {
        //given
        when(departmentRepository.save(any(Department.class)))
                .thenReturn(getSingleDepartment(1L));

        DepartmentDto departmentDTO = getSingleDepartmentDto(1L);

        //when
        DepartmentDto savedDepartment = departmentService.save(departmentDTO);

        //then
        assertNotNull(savedDepartment.getId());
    }

    @Test
    @DisplayName("Update a Department")
    public void givenSavedDepartment_whenUpdate_thenDepartmentIsUpdated() {
        //given
        when(departmentRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getSingleDepartment(1L)));

        when(departmentRepository.save(any(Department.class)))
                .thenReturn(getSingleDepartment(2L));

        es.codeurjc.daw.controller.dto.DepartmentDto toBeUpdatedDepartmentDto = getSingleDepartmentDto(2L);

        //when
        DepartmentDto updatedDepartmentDto = departmentService.update(1L, toBeUpdatedDepartmentDto);

        //then
        assertEquals(toBeUpdatedDepartmentDto.getDepartmentName(), updatedDepartmentDto.getDepartmentName());
        
    }
}
