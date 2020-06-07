package es.codeurjc.daw.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import es.codeurjc.daw.entity.Employee;

@DataJpaTest
@DisplayName("Integration Tests of EmployeeRepository with H2 Database")
public class EmployeeRepositoryITCase {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Get a Employee by Id")
    public void givenSingleEmployee_whenFindById_thenGetEmployee() {
        //given

        entityManager.persist(singleEmployee(11L));
        entityManager.flush();
        Employee savedEmployee = getEmployeeResultListSavedInDb().get(0);

        //when
        Optional<Employee> optionalEmployee = employeeRepository.findById(savedEmployee.getId());

        //then
        assertFalse(!optionalEmployee.isPresent());
        assertEquals("First name 11", optionalEmployee.get().getFirstName());
    }

    @Test
    @DisplayName("Get a list with 3 Employees")
    public void given3Employees_whenFindAll_thenGetEmployees() {
        //given
        entityManager.persist(singleEmployee(1L));
        entityManager.persist(singleEmployee(2L));
        entityManager.persist(singleEmployee(3L));
        entityManager.flush();

        //when
        Iterable<Employee> employeeIterable = employeeRepository.findAll();

        //then
        List<Employee> employeeList =
                StreamSupport.stream(employeeIterable.spliterator(), false)
                        .collect(Collectors.toList());

        assertFalse(employeeList.isEmpty());
        assertEquals(3, employeeList.size());
    }

    @Test
    @DisplayName("Get a Employee by Id when 2 Employees are in database")
    public void given2Employees_whenFindById_thenGetSingleEmployee() {
        //given
        entityManager.persist(singleEmployee(1L));
        entityManager.persist(singleEmployee(2L));
        entityManager.flush();

        Employee savedEmployee = getEmployeeResultListSavedInDb().get(0);

        //when
        Optional<Employee> optEmployee = employeeRepository.findById(savedEmployee.getId());

        //then
        assertTrue(optEmployee.isPresent());
        assertNotNull(optEmployee.get().getId());
    }

    @Test
    @DisplayName("Save a Employee")
    public void givenSingleEmployee_whenSave_thenEmployeeIsSaved() {
        //given
        Employee employee = singleEmployee(1L);

        //when
        employeeRepository.save(employee);

        //then
        Employee savedEmployee = getEmployeeResultListSavedInDb().get(0);
        assertNotNull(savedEmployee.getId());
        assertEquals("First name 1", savedEmployee.getFirstName());
    }

    @Test
    @DisplayName("Delete Employee by Id")
    public void given2SavedEmployees_whenDeleteById_thenOnlyOneEmployeeInDb() {
        //given
        entityManager.persist(singleEmployee(1L));
        entityManager.persist(singleEmployee(2L));
        entityManager.flush();

        Employee deletedEmployee = getEmployeeResultListSavedInDb().get(0);

        //when
        employeeRepository.deleteById(deletedEmployee.getId());

        //then
        List<Employee> employeeList = getEmployeeResultListSavedInDb();

        assertEquals(1, employeeList.size());
        assertFalse(deletedEmployee.getId().equals(employeeList.get(0).getId()));
    }

    private Employee singleEmployee(Long number){
        return Employee.builder()
                .firstName("First name " + number)
                .lastName("Last name " + number)
                .salary(1000*number)
                .build();
    }

    private List<Employee> getEmployeeResultListSavedInDb() {
        return entityManager
                .createNativeQuery("SELECT Employee.* FROM Employee", Employee.class)
                .getResultList();
    }
}
