package es.codeurjc.daw.service;

import static es.codeurjc.daw.mapper.DepartmentMapper.INSTANCE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.codeurjc.daw.controller.dto.DepartmentDto;
import es.codeurjc.daw.entity.Department;
import es.codeurjc.daw.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentService implements CrudService<DepartmentDto> {

	private final DepartmentRepository departmentRepository;

	@Override
	public List<DepartmentDto> findAll() {
		List<DepartmentDto> departmentDTOList = new ArrayList<>();
		departmentRepository.findAll().forEach(department -> departmentDTOList.add(INSTANCE.departmentToDto(department)));
		return departmentDTOList;
	}

	@Override
	public Optional<DepartmentDto> findById(Long id) {
		Optional<Department> departmentOptional = departmentRepository.findById(id);
		return departmentOptional.map(INSTANCE::departmentToDto);
	}

	@Override
	@Transactional
	public DepartmentDto save(DepartmentDto departmentDTO) {
		Department department = INSTANCE.dtoToDepartment(departmentDTO);
		return INSTANCE.departmentToDto(departmentRepository.save(department));
	}

	@Override
	@Transactional
	public void delete(Long id) {
		departmentRepository.deleteById(id);
	}

	@Override
	@Transactional
	public DepartmentDto update(Long id, DepartmentDto departmentDTO) {
		Department savedDepartment = departmentRepository.findById(id).orElseThrow();
		Department departmentToUpdate = INSTANCE.dtoToDepartment(departmentDTO);
		savedDepartment.setDepartmentName(departmentToUpdate.getDepartmentName());
		return INSTANCE.departmentToDto(departmentRepository.save(savedDepartment));
	}
}
