package es.codeurjc.daw.service;

import static es.codeurjc.daw.mapper.EmployeeMapper.INSTANCE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.codeurjc.daw.controller.dto.EmployeeDto;
import es.codeurjc.daw.entity.Employee;
import es.codeurjc.daw.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService implements CrudService<EmployeeDto> {

	private final EmployeeRepository employeeRepository;

	@Override
	public List<EmployeeDto> findAll() {
		List<EmployeeDto> employeeDTOList = new ArrayList<>();
		employeeRepository.findAll().forEach(employee -> employeeDTOList.add(INSTANCE.employeeToDto(employee)));
		return employeeDTOList;
	}

	@Override
	public Optional<EmployeeDto> findById(Long id) {
		Optional<Employee> employeeOptional = employeeRepository.findById(id);
		return employeeOptional.map(INSTANCE::employeeToDto);
	}

	@Override
	@Transactional
	public EmployeeDto save(EmployeeDto employeeDTO) {
		Employee employee = INSTANCE.dtoToEmployee(employeeDTO);
		return INSTANCE.employeeToDto(employeeRepository.save(employee));
	}

	@Override
	@Transactional
	public void delete(Long id) {
		employeeRepository.deleteById(id);
	}

	@Override
	@Transactional
	public EmployeeDto update(Long id, EmployeeDto employeeDTO) {
		Employee savedEmployee = employeeRepository.findById(id).orElseThrow();
		Employee employeeToUpdate = INSTANCE.dtoToEmployee(employeeDTO);
		savedEmployee.setFirstName(employeeToUpdate.getFirstName());
		savedEmployee.setLastName(employeeToUpdate.getLastName());
		savedEmployee.setSalary(employeeToUpdate.getSalary());
		return INSTANCE.employeeToDto(employeeRepository.save(savedEmployee));
	}
}
