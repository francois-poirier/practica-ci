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

import es.codeurjc.daw.controller.dto.EmployeeDto;
import es.codeurjc.daw.service.EmployeeService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
@DisplayName("Unit tests of EmployeeController")
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;

	@Test
	@DisplayName("GET an empty list of Employees")
	public void givenNoEmployees_whenGETEmployees_thenGetEmptyList() throws Exception {
		// given
		when(employeeService.findAll()).thenReturn(Collections.emptyList());

		// when
		mockMvc.perform(get("/employees/"))
				// then
				.andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(0)));

	}

	@Test
	@DisplayName("GET a list with single Employee")
	public void givenSingleEmployee_whenGETEmployees_thenGetSingleEmployeeList() throws Exception {
		// given
		when(employeeService.findAll()).thenReturn(getEmployeeListDTO(1L));

		mockMvc.perform(get("/employees/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(1)));
	}

	@Test
	@DisplayName("GET a Employee by Id")
	public void givenEmployeeId_whenGETEmployeesById_thenGetSingleEmployee() throws Exception {
		// given
		when(employeeService.findById(1L)).thenReturn(Optional.of(getSingleEmployeeDto(1L)));

		// when & then
		mockMvc.perform(get("/employees/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.firstName").value("First name 1"));
	}

	@Test
	@DisplayName("GET a Employee by Id and return 404 Not Found")
	public void givenIncorrectEmployeeId_whenGETEmployeesById_thenGetNotFoundEmployee() throws Exception {
		// given
		when(employeeService.findById(1L)).thenReturn(Optional.empty());

		// when & then
		mockMvc.perform(get("/employees/1")).andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("POST a Employee to create it")
	public void givenEmployee_whenPOSTSave_thenGetSavedEmployee() throws Exception {
		// given
		EmployeeDto employeeDTO = getSingleEmployeeDto(1L);
		employeeDTO.setId(null);
        
		// when
		mockMvc.perform(post("/employees/").contentType(MediaType.APPLICATION_JSON).content(asJsonString(employeeDTO))
				.characterEncoding("utf-8")

		)

				.andDo(print()).andExpect(status().isCreated());
	}

	@Test
	@DisplayName("DELETE a Employee by Id")
	public void givenEmployeeId_whenDELETEEmployee_thenEmployeeIsDeleted() throws Exception {
		// given
		Long employeeId = 1L;
		when(employeeService.findById(1L)).thenReturn(Optional.of(getSingleEmployeeDto(1L)));

		// when
		mockMvc.perform(delete("/employees/" + employeeId)).andDo(print()).andExpect(status().isNoContent());
	}

	@Test
	@DisplayName("DELETE a Employee by Id and return 404 HTTP Not Found")
	public void givenEmployeeId_whenDELETEEmployee_thenEmployeeNotFound() throws Exception {
		// given
		Long employeeId = 1L;
		when(employeeService.findById(1L)).thenReturn(Optional.empty());

		// when
		mockMvc.perform(delete("/employees/" + employeeId)).andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("PUT a Employee by Id to update it")
	public void givenIdAndUpdatedEmployee_whenPUTUpdate_thenEmployeeIsUpdated() throws Exception {
		// given
		Long employeeId = 1L;
		EmployeeDto employeeDTO = getSingleEmployeeDto(1L);

		when(employeeService.findById(1L)).thenReturn(Optional.of(employeeDTO));

		EmployeeDto updatedEmployee = employeeDTO;
		updatedEmployee.setFirstName("Albert");
		updatedEmployee.setLastName("Gomez");
		updatedEmployee.setSalary(2500L);

		// when
		mockMvc.perform(put("/employees/" + employeeId).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(employeeDTO)).characterEncoding("utf-8")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string("Object with id 1 was updated."));

	}

	@Test
	@DisplayName("PUT a Employee by Id to update it and return 404 HTTP Not Found")
	public void givenIdAndUpdatedEmployee_whenPUTUpdate_thenEmployeeNotFound() throws Exception {
		// given
		Long employeeId = 1L;
		EmployeeDto employeeDTO = getSingleEmployeeDto(1L);

		when(employeeService.findById(1L)).thenReturn(Optional.empty());

		// when
		mockMvc.perform(put("/employees/" + employeeId).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(employeeDTO)).characterEncoding("utf-8")).andDo(print())
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
