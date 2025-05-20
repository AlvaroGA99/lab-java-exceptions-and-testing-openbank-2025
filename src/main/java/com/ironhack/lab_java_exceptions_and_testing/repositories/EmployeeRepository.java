package com.ironhack.lab_java_exceptions_and_testing.repositories;

import com.ironhack.lab_java_exceptions_and_testing.EmployeeStatus;
import com.ironhack.lab_java_exceptions_and_testing.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository  extends JpaRepository<Employee, Long> {

    List<Employee> findByStatus(EmployeeStatus status);

    List<Employee> findByDepartment(String department);


}
