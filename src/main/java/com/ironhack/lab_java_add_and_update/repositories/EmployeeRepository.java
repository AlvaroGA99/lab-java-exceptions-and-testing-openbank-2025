package com.ironhack.lab_java_add_and_update.repositories;

import com.ironhack.lab_java_add_and_update.EmployeeStatus;
import com.ironhack.lab_java_add_and_update.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository  extends JpaRepository<Employee, Long> {

    List<Employee> findByStatus(EmployeeStatus status);

    List<Employee> findByDepartment(String department);


}
