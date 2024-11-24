package com.gruppe6.econsult.Patientenverwaltung.Application;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gruppe6.econsult.Patientenverwaltung.Entity.Patient;
import com.gruppe6.econsult.Patientenverwaltung.Service.PatientenService;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientenService patientenService;

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Optional<Patient> patient = patientenService.getPatientById(id);
        if (patient.isPresent()) {
            return ResponseEntity.ok(patient.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        Optional<Patient> patient = patientenService.getPatientByUsername(username);
        if (patient.isPresent() && patient.get().getPassword().equals(password)) {
            if (!patient.get().getRoll()) { // Role: patient
                return ResponseEntity.ok("Logged in as: normal");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    // Register API: Registers a new Patient
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
