package es.codeurjc.daw.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Department extends BaseEntity {

	private String departmentName;

	@OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Employee> employees = new ArrayList<>();

	public void addEmployee(Employee employee) {
		employees.add(employee);
		employee.setDepartment(this);
	}

	public void removeEmployee(Employee employee) {
		employees.remove(employee);
		employee.setDepartment(null);
	}
}
