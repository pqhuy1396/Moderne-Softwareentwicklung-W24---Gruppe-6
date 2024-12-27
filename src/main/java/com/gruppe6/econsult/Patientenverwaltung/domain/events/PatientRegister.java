package com.gruppe6.econsult.Patientenverwaltung.domain.events;

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
public class PatientRegister {

    private final PatientenService patientenService;

    @Autowired
    public PatientRegister(PatientenService patientenService) {
        this.patientenService = patientenService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String address, // Required for Patient
            @RequestParam String birthDate // Required for Patient
    ) {
        if (address == null || birthDate == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing address or birthDate for Patient");
        }
        Long id = patientenService.generateRandomId();
        Patient newPatient = new Patient(id, name, false, address, birthDate, email, username, password);
        patientenService.savePatient(newPatient);
        return ResponseEntity.status(HttpStatus.CREATED).body("Patient registered successfully");
    }
}