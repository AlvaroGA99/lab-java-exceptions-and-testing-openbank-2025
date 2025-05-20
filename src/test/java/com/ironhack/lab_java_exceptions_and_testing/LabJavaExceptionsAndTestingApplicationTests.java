package com.ironhack.lab_java_exceptions_and_testing;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.lab_java_exceptions_and_testing.EmployeeStatus;

import com.ironhack.lab_java_exceptions_and_testing.dtos.EmployeeDepartmentDto;
import com.ironhack.lab_java_exceptions_and_testing.dtos.EmployeeStatusDto;
import com.ironhack.lab_java_exceptions_and_testing.models.Employee;
import com.ironhack.lab_java_exceptions_and_testing.models.Patient;
import com.ironhack.lab_java_exceptions_and_testing.repositories.EmployeeRepository;
import com.ironhack.lab_java_exceptions_and_testing.repositories.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertTrue;


import java.sql.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class LabJavaExceptionsAndTestingApplicationTests {

	@Autowired
	WebApplicationContext wac;

	private final ObjectMapper objectMapper = new ObjectMapper();
	MockMvc mockMvc;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	PatientRepository patientRepository;

//
//
//	@MockitoBean
//	PatientRepository mockPatientRepository;



	Employee employee;
	Patient patient;


	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		employee = new Employee();
		employee.setDepartment("Cardiology");
		employee.setStatus(EmployeeStatus.ON);
		employee.setName("John Doe");
		employeeRepository.save(employee);
		patient = new Patient();
		patient.setName("Jane Doe");
		patient.setDateOfBirth(Date.valueOf("1990-01-01"));
		patient.setAddmittedBy(employee);
		patientRepository.save(patient);


	}

	@AfterEach
	public void tearDown() {
		patientRepository.deleteAll();
		employeeRepository.deleteAll();

	}

	@Test
	public void testGetAllPatients() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/patients") )
				.andExpect(status().isOk())
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		assertTrue(content.contains("Jane Doe"));


	}
	@Test
	public void testGetPatientById() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/patients/1") )
				.andExpect(status().isOk())
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		assertTrue(content.contains("John Doe"));

	}
	@Test
	public void testGetPatienyByIdNotFound() throws Exception {
//		Mockito.when(mockPatientRepository.getById(1L))
//				.thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
//
//		MvcResult mvcResult = mockMvc.perform(get("/patients/1") )
//				.andExpect(status().isNotFound())
//				.andReturn();

	}
	@Test
	public void testGetPatientsByBirthYearRange() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/patients?startYear=1990&endYear=2000") )
				.andExpect(status().isOk())
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		assertTrue(content.contains("Jane Doe"));


	}
	@Test
	public void testGetPatientsByDoctorDepartment() throws Exception{
		MvcResult mvcResult = mockMvc.perform(get("/patients?department=Cardiology") )
				.andExpect(status().isOk())
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		assertTrue(content.contains("Jane Doe"));

	}
	@Test
	public void testGetPatientsByDoctorStatus() throws Exception{
		MvcResult mvcResult = mockMvc.perform(get("/patients?status=ON") )
				.andExpect(status().isOk())
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		assertTrue(content.contains("Jane Doe"));

	}
	@Test
	public void testPost() throws Exception{
		String json = objectMapper.writeValueAsString(employee);

		mockMvc.perform(post("/employees")
						.contentType("application/json")
						.content(json))
				.andExpect(status().isCreated());
		List<Employee> employees = employeeRepository.findAll();
		assertTrue(employees.size() > 0);
		assertTrue(employees.get(0).getName().equals("John Doe"));
		assertTrue(employees.get(0).getDepartment().equals("Cardiology"));
		assertTrue(employees.get(0).getStatus().equals(EmployeeStatus.ON));

	}
	@Test
	public void testUpdate() throws Exception{
		String json = objectMapper.writeValueAsString(patient);

		mockMvc.perform(put("/patients/1")
						.contentType("application/json")
						.content(json))
				.andExpect(status().isOk());
		List<Employee> employees = employeeRepository.findAll();
		assertTrue(employees.size() > 0);
		assertTrue(employees.get(0).getName().equals("John Doe"));
		assertTrue(employees.get(0).getDepartment().equals("Cardiology"));
		assertTrue(employees.get(0).getStatus().equals(EmployeeStatus.ON));

	}
	//@Test
//	public void testUpdateNotFound() throws Exception{
//
//	}
	@Test
	public void testgetAllEmployees() throws Exception{
		MvcResult mvcResult = mockMvc.perform(get("/employees") )
				.andExpect(status().isOk())
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		assertTrue(content.contains("John Doe"));
	}
	@Test
	public void testgetEmployeeById() throws Exception{
		MvcResult mvcResult = mockMvc.perform(get("/employees/1") )
				.andExpect(status().isOk())
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		assertTrue(content.contains("John Doe"));
	}
	//@Test
//	public void testgetEmployeeByIdNotFound() throws Exception{
//	}
	@Test
	public void testgetEmployeesByStatus() throws Exception{
		MvcResult mvcResult = mockMvc.perform(get("/employees?status=ON") )
				.andExpect(status().isOk())
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		assertTrue(content.contains("John Doe"));
	}
	@Test
	public void testgetEmployeesByDepartment() throws Exception{
		MvcResult mvcResult = mockMvc.perform(get("/employees?department=Cardiology") )
				.andExpect(status().isOk())
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		assertTrue(content.contains("John Doe"));
	}
	@Test
	public void testcreateEmployee() throws Exception{

		String json = objectMapper.writeValueAsString(employee);

		mockMvc.perform(post("/employees")
						.contentType("application/json")
						.content(json))
				.andExpect(status().isCreated());
		List<Employee> employees = employeeRepository.findAll();
		assertTrue(employees.size() > 0);
		assertTrue(employees.get(0).getName().equals("John Doe"));
		assertTrue(employees.get(0).getDepartment().equals("Cardiology"));
		assertTrue(employees.get(0).getStatus().equals(EmployeeStatus.ON));
	}
	@Test
	public void testupdateEmployeeStatus() throws Exception{
		EmployeeStatusDto employeeStatusDto = new EmployeeStatusDto();
		employeeStatusDto.setStatus(EmployeeStatus.OFF);
		String json = objectMapper.writeValueAsString(employeeStatusDto);

		mockMvc.perform(patch("/employees/1/status")
						.contentType("application/json")
						.content(json))
				.andExpect(status().isOk());
		List<Employee> employees = employeeRepository.findAll();
		assertTrue(employees.size() > 0);
		assertTrue(employees.get(0).getName().equals("John Doe"));
		assertTrue(employees.get(0).getDepartment().equals("Cardiology"));
		assertTrue(employees.get(0).getStatus().equals(EmployeeStatus.ON));
	}
	//@Test
//	public void testupdateEmployeeStatusNotFound() throws Exception{
//	}
	@Test
	public void testupdateEmployeeDepartment() throws Exception{
		EmployeeDepartmentDto employeeDepartmentDto = new EmployeeDepartmentDto();
		employeeDepartmentDto.setDepartment("Neurology");

		String json = objectMapper.writeValueAsString(employeeDepartmentDto);

		mockMvc.perform(patch("/employees/1/department")
						.contentType("application/json")
						.content(json))
				.andExpect(status().isOk());
		List<Employee> employees = employeeRepository.findAll();
		assertTrue(employees.size() > 0);
		assertTrue(employees.get(0).getName().equals("John Doe"));
		assertTrue(employees.get(0).getDepartment().equals("Cardiology"));
		assertTrue(employees.get(0).getStatus().equals(EmployeeStatus.ON));
	}
	//@Test
//	public void testupdateEmployeeDepartmentNotFound() throws Exception{
//	}





}
