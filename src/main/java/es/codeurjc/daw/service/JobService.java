package es.codeurjc.daw.service;

import static es.codeurjc.daw.mapper.JobMapper.INSTANCE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.codeurjc.daw.controller.dto.JobDto;
import es.codeurjc.daw.entity.Job;
import es.codeurjc.daw.repository.JobRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobService implements CrudService<JobDto> {

	private final JobRepository jobRepository;

	@Override
	public List<JobDto> findAll() {
		List<JobDto> jobDTOList = new ArrayList<>();
		jobRepository.findAll().forEach(job -> jobDTOList.add(INSTANCE.jobToDto(job)));
		return jobDTOList;
	}

	@Override
	public Optional<JobDto> findById(Long id) {
		Optional<Job> jobOptional = jobRepository.findById(id);
		return jobOptional.map(INSTANCE::jobToDto);
	}

	@Override
	@Transactional
	public JobDto save(JobDto jobDTO) {
		Job job = INSTANCE.dtoToJob(jobDTO);
		return INSTANCE.jobToDto(jobRepository.save(job));
	}

	@Override
	@Transactional
	public void delete(Long id) {
		jobRepository.deleteById(id);
	}

	@Override
	@Transactional
	public JobDto update(Long id, JobDto jobDTO) {
		Job savedJob = jobRepository.findById(id).orElseThrow();
		Job jobToUpdate = INSTANCE.dtoToJob(jobDTO);
		savedJob.setJobTitle(jobToUpdate.getJobTitle());
		savedJob.setMaxSalary(jobToUpdate.getMaxSalary());
		savedJob.setMinSalary(jobToUpdate.getMinSalary());
		return INSTANCE.jobToDto(jobRepository.save(savedJob));
	}
}
