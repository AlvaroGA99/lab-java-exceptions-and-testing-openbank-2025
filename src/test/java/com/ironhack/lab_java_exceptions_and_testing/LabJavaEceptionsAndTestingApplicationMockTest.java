package com.ironhack.lab_java_exceptions_and_testing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.lab_java_exceptions_and_testing.dtos.EmployeeDepartmentDto;
import com.ironhack.lab_java_exceptions_and_testing.dtos.EmployeeStatusDto;
import com.ironhack.lab_java_exceptions_and_testing.models.Employee;
import com.ironhack.lab_java_exceptions_and_testing.models.Patient;
import com.ironhack.lab_java_exceptions_and_testing.repositories.EmployeeRepository;
import com.ironhack.lab_java_exceptions_and_testing.repositories.PatientRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class LabJavaEceptionsAndTestingApplicationMockTest
{

    @Autowired
    WebApplicationContext wac;

    private final ObjectMapper objectMapper = new ObjectMapper();
    MockMvc mockMvc;


    @MockitoBean
    @Autowired
    EmployeeRepository mockEmployeeRepository;
	@MockitoBean
    @Autowired
	PatientRepository mockPatientRepository;


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Test
    public void testGetPatienyByIdNotFound() throws Exception {
		Mockito.when(mockPatientRepository.getById(1L))
				.thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

		MvcResult mvcResult = mockMvc.perform(get("/patients/1") )
				.andExpect(status().isNotFound())
				.andReturn();

    }
    @Test
	public void testUpdateNotFound() throws Exception{
        Mockito.when(mockPatientRepository.save(any(Patient.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        Patient patient = new Patient();
        patient.setName("John Doe");
        patient.setDateOfBirth(Date.valueOf("1990-01-01"));
        patient.setAddmittedBy(new Employee());
        MvcResult mvcResult = mockMvc.perform(put("/patients/1")
                .content(objectMapper.writeValueAsString(patient)).contentType("application/json"))
                .andExpect(status().isNotFound())
                .andReturn();

    }


    @Test
	public void testgetEmployeeByIdNotFound() throws Exception{
        Mockito.when(mockEmployeeRepository.getById(1L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        MvcResult mvcResult = mockMvc.perform(get("/employees/1") )
                .andExpect(status().isNotFound())
                .andReturn();
	}
    @Test
	public void testupdateEmployeeStatusNotFound() throws Exception{
        Mockito.when(mockEmployeeRepository.save(any(Employee.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        MvcResult mvcResult = mockMvc.perform(patch("/employees/1/status")
                        .content(objectMapper.writeValueAsString(new EmployeeStatusDto())).contentType("application/json"))
                .andExpect(status().isNotFound())
                .andReturn();
	}
    @Test
	public void testupdateEmployeeDepartmentNotFound() throws Exception{
        Mockito.when(mockEmployeeRepository.save(any(Employee.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        MvcResult mvcResult = mockMvc.perform(patch("/employees/1/department")
                        .content(objectMapper.writeValueAsString(new EmployeeDepartmentDto())).contentType("application/json"))
                .andExpect(status().isNotFound())
                .andReturn();
    }






}
