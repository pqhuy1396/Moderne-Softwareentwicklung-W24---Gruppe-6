package com.gruppe6.econsult.controller;

import com.gruppe6.econsult.model.Patient;
import com.gruppe6.econsult.model.Arzt;
import com.gruppe6.econsult.service.PatientenService;
import com.gruppe6.econsult.service.ArztService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private PatientenService patientenService;

    @Autowired
    private ArztService arztService;

    // Login API: Verifies login credentials and determines role
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        // Check if the user is a doctor
        Optional<Arzt> arzt = arztService.getArztByUsername(username);
        if (arzt.isPresent() && arzt.get().getPassword().equals(password)) {
            if (arzt.get().getRoll()) { // Role: doctor
                return ResponseEntity.ok("Logged in as: special");
            }
        }

        // Check if the user is a patient
        Optional<Patient> patient = patientenService.getPatientByUsername(username);
        if (patient.isPresent() && patient.get().getPassword().equals(password)) {
            if (!patient.get().getRoll()) { // Role: patient
                return ResponseEntity.ok("Logged in as: normal");
            }
        }

        // Incorrect credentials
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    // Register API: Registers a new doctor or patient based on the role
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Boolean roll,  // true for doctor, false for patient
            @RequestParam(required = false) String fachrichtung,  // required for doctors
            @RequestParam(required = false) String lizenznummer,  // required for doctors
            @RequestParam(required = false) String address,       // required for patients
            @RequestParam(required = false) String birthDate      // required for patients
    ) {
        if (roll) { // Registering as a doctor
            if (fachrichtung == null || lizenznummer == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing fachrichtung or lizenznummer for Arzt");
            }

            Arzt newArzt = new Arzt(name, true, fachrichtung, lizenznummer, email, username, password);
            arztService.saveArzt(newArzt);
            return ResponseEntity.status(HttpStatus.CREATED).body("Arzt registered successfully");

        } else { // Registering as a patient
            if (address == null || birthDate == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing address or birthDate for Patient");
            }

            Patient newPatient = new Patient(name, false, address, birthDate, email, username, password);
            patientenService.savePatient(newPatient);
            return ResponseEntity.status(HttpStatus.CREATED).body("Patient registered successfully");
        }
    }
}
