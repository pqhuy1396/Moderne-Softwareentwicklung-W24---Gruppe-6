package com.gruppe6.econsult.Patientenverwaltung.domain.events;

import com.gruppe6.econsult.Patientenverwaltung.application.service.PatientenService;
import com.gruppe6.econsult.Patientenverwaltung.domain.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;



@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientenService patientenService;

    @Autowired
    public PatientController(PatientenService patientenService) {
        this.patientenService = patientenService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Optional<Patient> patient = patientenService.getPatientById(id);
        return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
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
