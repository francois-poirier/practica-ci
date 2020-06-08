package es.codeurjc.daw.service;

import static es.codeurjc.daw.util.TestDataFactory.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.codeurjc.daw.controller.dto.JobDto;
import es.codeurjc.daw.entity.Job;
import es.codeurjc.daw.repository.JobRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests of JobService class")
public class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobService jobService;

    @Test
    @DisplayName("Get an empty list of Jobs")
    public void givenNoJobs_whenFindAllJobs_thenGetEmptyList() {
        //given
        when(jobRepository.findAll())
                .thenReturn(Collections.emptyList());

        //when
        List<JobDto> jobList = jobService.findAll();

        //then
        assertEquals(0, jobList.size());
    }

    @Test
    @DisplayName("Get a list with single Job")
    public void givenSingleJobs_whenFindAllJobs_thenSingleJobList() {
        //given
        when(jobRepository.findAll())
                .thenReturn(getJobList(1L));

        //when
        List<JobDto> jobList = jobService.findAll();

        //then
        assertEquals(1, jobList.size());
        assertEquals("Job title 1", jobList.get(0).getJobTitle());
    }

    @Test
    @DisplayName("Get a list of 500 Jobs")
    public void given500Jobs_whenFindAllJobs_then500JobList() {
        //given
        when(jobRepository.findAll())
                .thenReturn(getJobList(500L));

        //when
        List<JobDto> jobList = jobService.findAll();

        //then
        assertEquals(500, jobList.size());
    }

    @Test
    @DisplayName("Get a Job by Id")
    public void givenSingleJob_whenFindById_thenGetSingleJob(){
        //given
        when(jobRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getSingleJob(1L)));

        //when
        Optional<JobDto> optJobDto = jobService.findById(1L);

        //then
        assertTrue(optJobDto.isPresent());
        assertEquals("Job title 1", optJobDto.get().getJobTitle());
    }

    @Test
    @DisplayName("Get a Job by Id and return empty result")
    public void givenNoJob_whenFindById_thenGetEmptyOptional(){
        //given
        when(jobRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        //when
        Optional<JobDto> jobDTOOpt = jobService.findById(1L);

        //then
        assertFalse(jobDTOOpt.isPresent());
    }

    @Test
    @DisplayName("Save a Job")
    public void givenJob_whenSave_thenGetSavedJob() {
        //given
        when(jobRepository.save(any(Job.class)))
                .thenReturn(getSingleJob(1L));

        JobDto jobDTO = getSingleJobDto(1L);

        //when
        JobDto savedJob = jobService.save(jobDTO);

        //then
        assertNotNull(savedJob.getId());
    }

    @Test
    @DisplayName("Update a Job")
    public void givenSavedJob_whenUpdate_thenJobIsUpdated() {
        //given
        when(jobRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getSingleJob(1L)));

        when(jobRepository.save(any(Job.class)))
                .thenReturn(getSingleJob(2L));

        es.codeurjc.daw.controller.dto.JobDto toBeUpdatedJobDto = getSingleJobDto(2L);

        //when
        JobDto updatedJobDto = jobService.update(1L, toBeUpdatedJobDto);

        //then
        assertEquals(toBeUpdatedJobDto.getJobTitle(), updatedJobDto.getJobTitle());
        
    }
}
