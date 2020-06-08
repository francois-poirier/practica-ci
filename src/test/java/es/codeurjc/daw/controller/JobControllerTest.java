package es.codeurjc.daw.controller;

import static es.codeurjc.daw.util.TestDataFactory.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.codeurjc.daw.controller.dto.JobDto;
import es.codeurjc.daw.service.JobService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(JobController.class)
@DisplayName("Unit tests of JobController")
public class JobControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private JobService jobService;

	@Test
	@DisplayName("GET an empty list of Jobs")
	public void givenNoJobs_whenGETJobs_thenGetEmptyList() throws Exception {
		// given
		when(jobService.findAll()).thenReturn(Collections.emptyList());

		// when
		mockMvc.perform(get("/jobs/"))
				// then
				.andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(0)));

	}

	@Test
	@DisplayName("GET a list with single Job")
	public void givenSingleJob_whenGETJobs_thenGetSingleJobList() throws Exception {
		// given
		when(jobService.findAll()).thenReturn(getJobListDTO(1L));

		mockMvc.perform(get("/jobs/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(1)));
	}

	@Test
	@DisplayName("GET a Job by Id")
	public void givenJobId_whenGETJobsById_thenGetSingleJob() throws Exception {
		// given
		when(jobService.findById(1L)).thenReturn(Optional.of(getSingleJobDto(1L)));

		// when & then
		mockMvc.perform(get("/jobs/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.jobTitle").value("Job title 1"));
	}

	@Test
	@DisplayName("GET a Job by Id and return 404 Not Found")
	public void givenIncorrectJobId_whenGETJobsById_thenGetNotFoundJob() throws Exception {
		// given
		when(jobService.findById(1L)).thenReturn(Optional.empty());

		// when & then
		mockMvc.perform(get("/jobs/1")).andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("POST a Job to create it")
	public void givenJob_whenPOSTSave_thenGetSavedJob() throws Exception {
		// given
		JobDto jobDTO = getSingleJobDto(1L);
		jobDTO.setId(null);
        
		// when
		mockMvc.perform(post("/jobs/").contentType(MediaType.APPLICATION_JSON).content(asJsonString(jobDTO))
				.characterEncoding("utf-8")

		)

				.andDo(print()).andExpect(status().isCreated());
	}

	@Test
	@DisplayName("DELETE a Job by Id")
	public void givenJobId_whenDELETEJob_thenJobIsDeleted() throws Exception {
		// given
		Long jobId = 1L;
		when(jobService.findById(1L)).thenReturn(Optional.of(getSingleJobDto(1L)));

		// when
		mockMvc.perform(delete("/jobs/" + jobId)).andDo(print()).andExpect(status().isNoContent());
	}

	@Test
	@DisplayName("DELETE a Job by Id and return 404 HTTP Not Found")
	public void givenJobId_whenDELETEJob_thenJobNotFound() throws Exception {
		// given
		Long jobId = 1L;
		when(jobService.findById(1L)).thenReturn(Optional.empty());

		// when
		mockMvc.perform(delete("/jobs/" + jobId)).andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("PUT a Job by Id to update it")
	public void givenIdAndUpdatedJob_whenPUTUpdate_thenJobIsUpdated() throws Exception {
		// given
		Long jobId = 1L;
		JobDto jobDTO = getSingleJobDto(1L);

		when(jobService.findById(1L)).thenReturn(Optional.of(jobDTO));

		JobDto updatedJob = jobDTO;
		updatedJob.setJobTitle("Software Architect");
		updatedJob.setMaxSalary(60000L);
		updatedJob.setMinSalary(45000L);

		// when
		mockMvc.perform(put("/jobs/" + jobId).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(jobDTO)).characterEncoding("utf-8")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string("Object with id 1 was updated."));

	}

	@Test
	@DisplayName("PUT a Job by Id to update it and return 404 HTTP Not Found")
	public void givenIdAndUpdatedJob_whenPUTUpdate_thenJobNotFound() throws Exception {
		// given
		Long jobId = 1L;
		JobDto jobDTO = getSingleJobDto(1L);

		when(jobService.findById(1L)).thenReturn(Optional.empty());

		// when
		mockMvc.perform(put("/jobs/" + jobId).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(jobDTO)).characterEncoding("utf-8")).andDo(print())
				.andExpect(status().isNotFound());
	}

	private String asJsonString(Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
