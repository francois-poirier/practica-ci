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

import es.codeurjc.daw.controller.dto.DepartmentDto;
import es.codeurjc.daw.service.DepartmentService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DepartmentController.class)
@DisplayName("Unit tests of DepartmentController")
public class DepartmentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DepartmentService departmentService;

	@Test
	@DisplayName("GET an empty list of Departments")
	public void givenNoDepartments_whenGETDepartments_thenGetEmptyList() throws Exception {
		// given
		when(departmentService.findAll()).thenReturn(Collections.emptyList());

		// when
		mockMvc.perform(get("/departments/"))
				// then
				.andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(0)));

	}

	@Test
	@DisplayName("GET a list with single Department")
	public void givenSingleDepartment_whenGETDepartments_thenGetSingleDepartmentList() throws Exception {
		// given
		when(departmentService.findAll()).thenReturn(getDepartmentListDTO(1L));

		mockMvc.perform(get("/departments/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(1)));
	}

	@Test
	@DisplayName("GET a Department by Id")
	public void givenDepartmentId_whenGETDepartmentsById_thenGetSingleDepartment() throws Exception {
		// given
		when(departmentService.findById(1L)).thenReturn(Optional.of(getSingleDepartmentDto(1L)));

		// when & then
		mockMvc.perform(get("/departments/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.departmentName").value("Department name 1"));
	}

	@Test
	@DisplayName("GET a Department by Id and return 404 Not Found")
	public void givenIncorrectDepartmentId_whenGETDepartmentsById_thenGetNotFoundDepartment() throws Exception {
		// given
		when(departmentService.findById(1L)).thenReturn(Optional.empty());

		// when & then
		mockMvc.perform(get("/departments/1")).andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("POST a Department to create it")
	public void givenDepartment_whenPOSTSave_thenGetSavedDepartment() throws Exception {
		// given
		DepartmentDto departmentDTO = getSingleDepartmentDto(1L);
		departmentDTO.setId(null);

		// when
		mockMvc.perform(post("/departments/").contentType(MediaType.APPLICATION_JSON).content(asJsonString(departmentDTO))
				.characterEncoding("utf-8")

		)

				.andDo(print()).andExpect(status().isCreated());
	}

	@Test
	@DisplayName("DELETE a Department by Id")
	public void givenDepartmentId_whenDELETEDepartment_thenDepartmentIsDeleted() throws Exception {
		// given
		Long departmentId = 1L;
		when(departmentService.findById(1L)).thenReturn(Optional.of(getSingleDepartmentDto(1L)));

		// when
		mockMvc.perform(delete("/departments/" + departmentId)).andDo(print()).andExpect(status().isNoContent());
	}

	@Test
	@DisplayName("DELETE a Department by Id and return 404 HTTP Not Found")
	public void givenDepartmentId_whenDELETEDepartment_thenDepartmentNotFound() throws Exception {
		// given
		Long departmentId = 1L;
		when(departmentService.findById(1L)).thenReturn(Optional.empty());

		// when
		mockMvc.perform(delete("/departments/" + departmentId)).andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("PUT a Department by Id to update it")
	public void givenIdAndUpdatedDepartment_whenPUTUpdate_thenDepartmentIsUpdated() throws Exception {
		// given
		Long departmentId = 1L;
		DepartmentDto departmentDTO = getSingleDepartmentDto(1L);

		when(departmentService.findById(1L)).thenReturn(Optional.of(departmentDTO));

		DepartmentDto updatedDepartment = departmentDTO;
		updatedDepartment.setDepartmentName("New Department Name");

		// when
		mockMvc.perform(put("/departments/" + departmentId).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(departmentDTO)).characterEncoding("utf-8")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string("Object with id 1 was updated."));

	}

	@Test
	@DisplayName("PUT a Department by Id to update it and return 404 HTTP Not Found")
	public void givenIdAndUpdatedDepartment_whenPUTUpdate_thenDepartmentNotFound() throws Exception {
		// given
		Long departmentId = 1L;
		DepartmentDto departmentDTO = getSingleDepartmentDto(1L);

		when(departmentService.findById(1L)).thenReturn(Optional.empty());

		// when
		mockMvc.perform(put("/departments/" + departmentId).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(departmentDTO)).characterEncoding("utf-8")).andDo(print())
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
