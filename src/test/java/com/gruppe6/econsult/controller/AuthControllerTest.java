package com.gruppe6.econsult.controller;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gruppe6.econsult.model.Arzt;
import com.gruppe6.econsult.model.Patient;
import com.gruppe6.econsult.service.ArztService;
import com.gruppe6.econsult.service.PatientenService;

class AuthControllerTest {

    @Mock
    private PatientenService patientenService;

    @Mock
    private ArztService arztService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Tests for login functionality
    @Test
    void loginAsDoctor_case1() {
        // Test case: Successful login as a doctor
        Arzt mockArzt = new Arzt(123L, "Dr. John Doe", true, "Cardiology", "12345", "doctor@example.com", "doctorUser", "password123");
        when(arztService.getArztByUsername("doctorUser")).thenReturn(Optional.of(mockArzt));

        ResponseEntity<String> response = authController.login("doctorUser", "password123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Logged in as: special", response.getBody());
    }

    @Test
    void loginAsPatient_case2() {
        // Test case: Successful login as a patient
        Patient mockPatient = new Patient(456L, "Jane Doe", false, "patient@example.com", "01-01-1990", "123 Street", "patientUser", "password456");
        when(patientenService.getPatientByUsername("patientUser")).thenReturn(Optional.of(mockPatient));

        ResponseEntity<String> response = authController.login("patientUser", "password456");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Logged in as: normal", response.getBody());
    }

    @Test
    void loginInvalidCredentials_case3() {
        // Test case: Invalid credentials
        when(arztService.getArztByUsername("wrongUser")).thenReturn(Optional.empty());
        when(patientenService.getPatientByUsername("wrongUser")).thenReturn(Optional.empty());

        ResponseEntity<String> response = authController.login("wrongUser", "wrongPassword");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid username or password", response.getBody());
    }

    // Tests for register functionality
    @Test
    void registerDoctor_case1() {
        // Test case: Successful registration as a doctor
        Arzt newArzt = new Arzt(123L, "Dr. Smith", true, "Neurology", "98765", "drsmith@example.com", "drsmith", "securePass");
        when(arztService.generateRandomId()).thenReturn(123L);

        ResponseEntity<String> response = authController.register(
                newArzt.getName(), newArzt.getEmail(), newArzt.getUsername(), newArzt.getPassword(),
                true, newArzt.getFachrichtung(), newArzt.getLizenznummer(), null, null);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Arzt registered successfully", response.getBody());
    }

    @Test
    void registerPatient_case2() {
        // Test case: Successful registration as a patient
        Patient newPatient = new Patient(456L, "Alice Johnson", false, "alice@example.com", "1992-05-15", "456 Avenue", "alice", "pass123");
        when(patientenService.generateRandomId()).thenReturn(456L);

        ResponseEntity<String> response = authController.register(
                newPatient.getName(), newPatient.getEmail(), newPatient.getUsername(), newPatient.getPassword(),
                false, null, null, newPatient.getAddress(), newPatient.getBirthDate());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Patient registered successfully", response.getBody());
    }

    @Test
    void registerDoctor_missingFields_case3() {
        // Test case: Missing required fields for doctor registration
        ResponseEntity<String> response = authController.register(
                "Dr. Smith", "drsmith@example.com", "drsmith", "securePass",
                true, null, null, null, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Missing fachrichtung or lizenznummer for Arzt", response.getBody());
    }

    @Test
    void registerPatient_missingFields_case4() {
        // Test case: Missing required fields for patient registration
        ResponseEntity<String> response = authController.register(
                "Alice Johnson", "alice@example.com", "alice", "pass123",
                false, null, null, null, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Missing address or birthDate for Patient", response.getBody());
    }

    @Test
    void registerInvalidRole_case5() {
        // Test case: Invalid role (e.g., roll is null)
        ResponseEntity<String> response = authController.register(
                "Invalid User", "invalid@example.com", "invalidUser", "password123",
                null, null, null, null, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid role specified", response.getBody());
    }
}
