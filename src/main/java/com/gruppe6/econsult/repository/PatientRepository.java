package com.gruppe6.econsult.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gruppe6.econsult.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByUsername(String username);
    Optional<Patient> findPatientById(Long id);
    Optional<Patient> findByEmail(String email);
    void deletePatientById(Long id);
}
