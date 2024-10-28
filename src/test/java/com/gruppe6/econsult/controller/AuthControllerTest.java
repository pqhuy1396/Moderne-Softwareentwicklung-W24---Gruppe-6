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

    @Test
    void testLoginAsDoctor() {
        Arzt mockArzt = new Arzt("Dr. John Doe", true, "Cardiology", "12345", "doctor@example.com", "doctorUser", "password123");
        when(arztService.getArztByUsername("doctorUser")).thenReturn(Optional.of(mockArzt));

        ResponseEntity<String> response = authController.login("doctorUser", "password123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Logged in as: special", response.getBody());
    }

    @Test
    void testLoginAsPatient() {
        Patient mockPatient = new Patient("Jane Doe", false, "patient@example.com", "01-01-1990", "123 Street", "patientUser", "password456");
        when(patientenService.getPatientByUsername("patientUser")).thenReturn(Optional.of(mockPatient));

        ResponseEntity<String> response = authController.login("patientUser", "password456");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Logged in as: normal", response.getBody());
    }

    @Test
    void testLoginInvalidCredentials() {
        when(arztService.getArztByUsername("wrongUser")).thenReturn(Optional.empty());
        when(patientenService.getPatientByUsername("wrongUser")).thenReturn(Optional.empty());

        ResponseEntity<String> response = authController.login("wrongUser", "wrongPassword");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid username or password", response.getBody());
    }

    @Test
    void testRegisterDoctor() {
        Arzt newArzt = new Arzt("Dr. Smith", true, "Neurology", "98765", "drsmith@example.com", "drsmith", "securePass");

        ResponseEntity<String> response = authController.register(
                newArzt.getName(), newArzt.getEmail(), newArzt.getUsername(), newArzt.getPassword(),
                true, newArzt.getFachrichtung(), newArzt.getLizenznummer(), null, null);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Arzt registered successfully", response.getBody());
    }

    @Test
    void testRegisterPatient() {
        Patient newPatient = new Patient("Alice Johnson", false, "alice@example.com", "1992-05-15", "456 Avenue", "alice", "pass123");

        ResponseEntity<String> response = authController.register(
                newPatient.getName(), newPatient.getEmail(), newPatient.getUsername(), newPatient.getPassword(),
                false, null, null, newPatient.getAddress(), newPatient.getBirthDate());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Patient registered successfully", response.getBody());
    }
}
