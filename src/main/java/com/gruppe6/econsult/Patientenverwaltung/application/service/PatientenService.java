package com.gruppe6.econsult.Patientenverwaltung.application.service;


import com.gruppe6.econsult.Patientenverwaltung.domain.model.Patient;
import com.gruppe6.econsult.Patientenverwaltung.infrastructure.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class PatientenService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientenService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findPatientById(id);
    }

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public void deletePatientById(Long id) {
        patientRepository.deletePatientById(id);
    }

    public Optional<Patient> getPatientByUsername(String username) {
        return patientRepository.findByUsername(username);
    }

    public Optional<Patient> getPatientByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    public Long generateRandomId() {
        return 1000000000L + new Random().nextLong() % 9000000000L;
    }
}
