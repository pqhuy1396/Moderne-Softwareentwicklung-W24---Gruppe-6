package com.gruppe6.econsult.Patientenverwaltung.domain.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gruppe6.econsult.Patientenverwaltung.application.service.PatientenService;
import com.gruppe6.econsult.Patientenverwaltung.domain.model.Patient;

@RestController
@RequestMapping("/api/patients")
public class PatientGet {

    private final PatientenService patientenService;

    @Autowired
    public PatientGet(PatientenService patientenService) {
        this.patientenService = patientenService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
    return patientenService.getPatientById(id)
                           .map(ResponseEntity::ok)
                           .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}