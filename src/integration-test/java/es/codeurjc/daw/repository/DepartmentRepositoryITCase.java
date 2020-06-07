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

import es.codeurjc.daw.entity.Department;

@DataJpaTest
@DisplayName("Integration Tests of DepartmentRepository with H2 Database")
public class DepartmentRepositoryITCase {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Get a Department by Id")
    public void givenSingleDepartment_whenFindById_thenGetDepartment() {
        //given

        entityManager.persist(singleDepartment(11L));
        entityManager.flush();
        Department savedDepartment = getDepartmentResultListSavedInDb().get(0);

        //when
        Optional<Department> optionalDepartment = departmentRepository.findById(savedDepartment.getId());

        //then
        assertFalse(!optionalDepartment.isPresent());
        assertEquals("Department 11", optionalDepartment.get().getDepartmentName());
    }

    @Test
    @DisplayName("Get a list with 3 Departments")
    public void given3Departments_whenFindAll_thenGetDepartments() {
        //given
        entityManager.persist(singleDepartment(1L));
        entityManager.persist(singleDepartment(2L));
        entityManager.persist(singleDepartment(3L));
        entityManager.flush();

        //when
        Iterable<Department> departmentIterable = departmentRepository.findAll();

        //then
        List<Department> departmentList =
                StreamSupport.stream(departmentIterable.spliterator(), false)
                        .collect(Collectors.toList());

        assertFalse(departmentList.isEmpty());
        assertEquals(3, departmentList.size());
    }

    @Test
    @DisplayName("Get a Department by Id when 2 Departments are in database")
    public void given2Departments_whenFindById_thenGetSingleDepartment() {
        //given
        entityManager.persist(singleDepartment(1L));
        entityManager.persist(singleDepartment(2L));
        entityManager.flush();

        Department savedDepartment = getDepartmentResultListSavedInDb().get(0);

        //when
        Optional<Department> optDepartment = departmentRepository.findById(savedDepartment.getId());

        //then
        assertTrue(optDepartment.isPresent());
        assertNotNull(optDepartment.get().getId());
    }

    @Test
    @DisplayName("Save a Department")
    public void givenSingleDepartment_whenSave_thenDepartmentIsSaved() {
        //given
        Department department = singleDepartment(1L);

        //when
        departmentRepository.save(department);

        //then
        Department savedDepartment = getDepartmentResultListSavedInDb().get(0);
        assertNotNull(savedDepartment.getId());
        assertEquals("Department 1", savedDepartment.getDepartmentName());
    }

    @Test
    @DisplayName("Delete Department by Id")
    public void given2SavedDepartments_whenDeleteById_thenOnlyOneDepartmentInDb() {
        //given
        entityManager.persist(singleDepartment(1L));
        entityManager.persist(singleDepartment(2L));
        entityManager.flush();

        Department deletedDepartment = getDepartmentResultListSavedInDb().get(0);

        //when
        departmentRepository.deleteById(deletedDepartment.getId());

        //then
        List<Department> departmentList = getDepartmentResultListSavedInDb();

        assertEquals(1, departmentList.size());
        assertFalse(deletedDepartment.getId().equals(departmentList.get(0).getId()));
    }

    private Department singleDepartment(Long number){
        return Department.builder()
                .departmentName("Department " + number)
                .build();
    }

    private List<Department> getDepartmentResultListSavedInDb() {
        return entityManager
                .createNativeQuery("SELECT Department.* FROM Department", Department.class)
                .getResultList();
    }
}
