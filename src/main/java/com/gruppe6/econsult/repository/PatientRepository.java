package com.gruppe6.econsult.repository;

import com.gruppe6.econsult.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByUsername(String username);
    Optional<Patient> findPatientById(Long id);
    void deletePatientById(Long id);
}
