package com.gruppe6.econsult.service;

import com.gruppe6.econsult.model.Patient;
import com.gruppe6.econsult.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientenService {

    @Autowired
    private PatientRepository patientRepository;

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
}
