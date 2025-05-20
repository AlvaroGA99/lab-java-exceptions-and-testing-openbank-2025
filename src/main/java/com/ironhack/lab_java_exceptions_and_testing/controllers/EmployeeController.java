package com.ironhack.lab_java_exceptions_and_testing.controllers;

import com.ironhack.lab_java_exceptions_and_testing.EmployeeStatus;
import com.ironhack.lab_java_exceptions_and_testing.dtos.EmployeeDepartmentDto;
import com.ironhack.lab_java_exceptions_and_testing.dtos.EmployeeStatusDto;
import com.ironhack.lab_java_exceptions_and_testing.models.Employee;
import com.ironhack.lab_java_exceptions_and_testing.repositories.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeRepository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));
    }

    @GetMapping(params = "status")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployeesByStatus(@RequestParam EmployeeStatus status) {
        return employeeRepository.findByStatus(status);
    }

    @GetMapping(params = "department")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployeesByDepartment(@RequestParam String department) {
        return employeeRepository.findByDepartment(department);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody @Valid Employee employee) {
        return employeeRepository.save(employee);
    }

    @PatchMapping("/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public Employee updateEmployeeStatus(@PathVariable Long id, @RequestBody EmployeeStatusDto employee) {
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));
        if(employee.getStatus() != null) {
            existingEmployee.setStatus(employee.getStatus());
        }
        return employeeRepository.save(existingEmployee);
    }

    @PatchMapping("/{id}/department")
    @ResponseStatus(HttpStatus.OK)
    public Employee updateEmployeeDepartment(@PathVariable Long id, @RequestBody EmployeeDepartmentDto employee) {
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

        if(employee.getDepartment() != null) {
            existingEmployee.setDepartment(employee.getDepartment());
        }

        return employeeRepository.save(existingEmployee);
    }
}
