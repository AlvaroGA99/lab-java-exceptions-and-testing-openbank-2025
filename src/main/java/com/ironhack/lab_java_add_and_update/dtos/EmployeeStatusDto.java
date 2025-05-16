package com.ironhack.lab_java_add_and_update.dtos;

import com.ironhack.lab_java_add_and_update.EmployeeStatus;

public class EmployeeStatusDto {
    EmployeeStatus status;

    public EmployeeStatusDto() {
    }

    public EmployeeStatusDto(EmployeeStatus status) {
        this.status = status;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "EmployeeStatusDto{" +
                "status=" + status +
                '}';
    }
}
