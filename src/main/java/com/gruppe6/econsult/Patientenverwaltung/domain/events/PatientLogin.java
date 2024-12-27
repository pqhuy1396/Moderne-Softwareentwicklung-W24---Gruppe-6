package com.gruppe6.econsult.Patientenverwaltung.domain.events;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gruppe6.econsult.Patientenverwaltung.application.service.PatientenService;
import com.gruppe6.econsult.Patientenverwaltung.domain.model.Patient;

@RestController
@RequestMapping("/api/patients")
public class PatientLogin {

    private final PatientenService patientenService;

    @Autowired
    public PatientLogin(PatientenService patientenService) {
        this.patientenService = patientenService;
    }

    @PostMapping("/login")
    public ResponseEntity<Patient> login(@RequestParam String username, @RequestParam String password) {
        Optional<Patient> patient = patientenService.getPatientByUsername(username);
        if (patient.isPresent() && patient.get().getPassword().equals(password)) {
            if (!patient.get().getRoll()) { 
                return ResponseEntity.ok(patient.get());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}