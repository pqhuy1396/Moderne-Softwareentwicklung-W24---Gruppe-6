package com.gruppe6.econsult.PatientenverwaltungTest.domain.events;

import com.gruppe6.econsult.Patientenverwaltung.application.service.PatientenService;
import com.gruppe6.econsult.Patientenverwaltung.domain.events.PatientController;
import com.gruppe6.econsult.Patientenverwaltung.domain.model.Patient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientControllerTest {

    @Mock
    private PatientenService patientenService;

    @InjectMocks
    private PatientController patientController;

    @Test
    void getPatientById_shouldReturnPatientWhenFound() {
        // Arrange
        Long patientId = 1L;
        Patient patient = new Patient(patientId, "John Doe", false, "john.doe@example.com", "1990-01-01", "123 Main St", "johndoe", "password");

        when(patientenService.getPatientById(patientId)).thenReturn(Optional.of(patient));

        // Act
        ResponseEntity<Patient> response = patientController.getPatientById(patientId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patient, response.getBody());
    }

    @Test
    void getPatientById_shouldReturnNotFoundWhenPatientNotExist() {
        // Arrange
        Long patientId = 1L;

        when(patientenService.getPatientById(patientId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Patient> response = patientController.getPatientById(patientId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void login_shouldReturnSuccessForValidCredentials() {
        // Arrange
        String username = "johndoe";
        String password = "password";
        Patient patient = new Patient(1L, "John Doe", false, "john.doe@example.com", "1990-01-01", "123 Main St", username, password);

        when(patientenService.getPatientByUsername(username)).thenReturn(Optional.of(patient));

        // Act
        ResponseEntity<String> response = patientController.login(username, password);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Logged in as: normal", response.getBody());
    }

    @Test
    void login_shouldReturnUnauthorizedForInvalidCredentials() {
        // Arrange
        String username = "johndoe";
        String password = "wrongpassword";
        Patient patient = new Patient(1L, "John Doe", false, "john.doe@example.com", "1990-01-01", "123 Main St", username, "password");

        when(patientenService.getPatientByUsername(username)).thenReturn(Optional.of(patient));

        // Act
        ResponseEntity<String> response = patientController.login(username, password);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid username or password", response.getBody());
    }

    @Test
    void register_shouldCreateNewPatient() {
        // Arrange
        String name = "John Doe";
        String email = "john.doe@example.com";
        String username = "johndoe";
        String password = "password";
        String address = "123 Main St";
        String birthDate = "1990-01-01";

        Long id = 1L;
        when(patientenService.generateRandomId()).thenReturn(id);

        // Act
        ResponseEntity<String> response = patientController.register(name, email, username, password, address, birthDate);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Patient registered successfully", response.getBody());
    }

    @Test
    void register_shouldReturnBadRequestForMissingParameters() {
        // Arrange
        String name = "John Doe";
        String email = "john.doe@example.com";
        String username = "johndoe";
        String password = "password";

        // Missing 'address' and 'birthDate'

        // Act
        ResponseEntity<String> response = patientController.register(name, email, username, password, null, null);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Missing address or birthDate for Patient", response.getBody());
    }
}
