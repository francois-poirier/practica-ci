package es.codeurjc.daw.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import es.codeurjc.daw.entity.Department;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {

}
