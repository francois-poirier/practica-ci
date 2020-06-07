package es.codeurjc.daw.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import es.codeurjc.daw.entity.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}
