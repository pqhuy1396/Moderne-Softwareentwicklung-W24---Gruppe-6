package com.gruppe6.econsult.Patientenverwaltung.Repository;

import java.util.Optional;

import com.gruppe6.econsult.Patientenverwaltung.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByUsername(String username);
    Optional<Patient> findPatientById(Long id);
    Optional<Patient> findByEmail(String email);
    void deletePatientById(Long id);
}
