package com.ironhack.lab_java_add_and_update.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;

@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long patientId;

    @NotEmpty
    @NotNull
    private String name;

    @NotNull
    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee addmittedBy;

    public Patient(Long patientId, String name, Date dateOfBirth, Employee addmittedBy) {
        this.patientId = patientId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.addmittedBy = addmittedBy;
    }

    public Patient() {
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Employee getAddmittedBy() {
        return addmittedBy;
    }

    public void setAddmittedBy(Employee addmittedBy) {
        this.addmittedBy = addmittedBy;
    }
}


