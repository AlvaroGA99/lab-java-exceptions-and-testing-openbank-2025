package com.ironhack.lab_java_add_and_update.repositories;

import com.ironhack.lab_java_add_and_update.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByDateOfBirthBetween(Date minBirthYear, Date maxBirthYear);

    //plain jPQl JOIN EMPLOYYE WITH PATIENT
    @Query("SELECT p FROM Patient p WHERE p.addmittedBy.department = :department")
    List<Patient> findByDoctorDepartment(@Param("department") String department);

    @Query("SELECT p FROM Patient p WHERE p.addmittedBy.status = 'OFF'")
    List<Patient> findByOffDoctorStatus();
}
