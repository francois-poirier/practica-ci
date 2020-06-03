package es.codeurjc.daw.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends BaseEntity {

	private String firstName;
	private String lastName;
	private Long salary;
	private LocalDate hireDate;
	
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
