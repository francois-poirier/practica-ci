package es.codeurjc.daw.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Job extends BaseEntity {
	
	private String jobTitle;
	private Long minSalary;
	private Long maxSalary;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

}
