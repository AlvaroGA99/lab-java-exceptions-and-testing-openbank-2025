package com.ironhack.lab_java_exceptions_and_testing.dtos;

public class EmployeeDepartmentDto {
    String department;

    public EmployeeDepartmentDto() {
    }

    public EmployeeDepartmentDto(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "EmployeeDepartmentDto{" +
                "department='" + department + '\'' +
                '}';
    }
}
