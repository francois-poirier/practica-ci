package es.codeurjc.daw.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Employee extends BaseEntity {

	private String firstName;
	private String lastName;
	private Long salary;
	private LocalDate hireDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private Department department;

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Job> jobs = new ArrayList<>();

	public void addJob(Job job) {
		jobs.add(job);
		job.setEmployee(this);
	}

	public void removeComment(Job job) {
		jobs.remove(job);
		job.setEmployee(null);
	}
}
