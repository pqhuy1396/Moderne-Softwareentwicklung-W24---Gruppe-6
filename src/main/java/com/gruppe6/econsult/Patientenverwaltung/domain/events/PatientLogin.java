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
public class PatientLogin {

    private final PatientenService patientenService;

    @Autowired
    public PatientLogin(PatientenService patientenService) {
        this.patientenService = patientenService;
    }

    @PostMapping("/login")
    public ResponseEntity<Patient> login(@RequestParam String username, @RequestParam String password) {
        return patientenService.getPatientByUsername(username)
        .filter(p -> p.getPassword().equals(password))
        .filter(p -> !p.getRoll())
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}