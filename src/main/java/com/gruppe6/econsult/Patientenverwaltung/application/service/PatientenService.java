package com.gruppe6.econsult.Patientenverwaltung.application.service;


import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gruppe6.econsult.Patientenverwaltung.domain.model.Patient;
import com.gruppe6.econsult.Patientenverwaltung.infrastructure.repository.PatientRepository;

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
        return new Random().longs(1000000000L, 10000000000L)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unable to generate random ID"));
    }

}
