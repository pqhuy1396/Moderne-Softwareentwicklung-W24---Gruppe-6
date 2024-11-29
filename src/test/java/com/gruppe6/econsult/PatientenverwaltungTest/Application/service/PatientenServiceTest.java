package com.gruppe6.econsult.PatientenverwaltungTest.application.service;

import com.gruppe6.econsult.Patientenverwaltung.application.service.PatientenService;
import com.gruppe6.econsult.Patientenverwaltung.domain.model.Patient;
import com.gruppe6.econsult.Patientenverwaltung.infrastructure.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientenServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientenService patientenService;

    @Test
    void testGetPatientById() {
        // Arrange
        Long patientId = 112L;
        Patient patient = new Patient(patientId, "Jane Doe", false, "patient@example.com", "01-01-1990", "123 Street", "patientUser", "password456");
        when(patientRepository.findPatientById(patientId)).thenReturn(Optional.of(patient));

        // Act
        Optional<Patient> result = patientenService.getPatientById(patientId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Jane Doe", result.get().getName());
        verify(patientRepository, times(1)).findPatientById(patientId);
    }

    @Test
    void testSavePatient() {
        // Arrange
        Patient patient = new Patient(112L, "Jane Doe", false, "patient@example.com", "01-01-1990", "123 Street", "patientUser", "password456");
        when(patientRepository.save(patient)).thenReturn(patient);

        // Act
        Patient result = patientenService.savePatient(patient);

        // Assert
        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    void testGetAllPatients() {
        // Arrange
        List<Patient> patients = Arrays.asList(
                new Patient(1L, "John Doe", false, "john@example.com", "1990-01-01", "123 Main St", "johndoe", "password"),
                new Patient(2L, "Jane Doe", false, "jane@example.com", "1985-05-20", "456 Elm St", "janedoe", "password123")
        );
        when(patientRepository.findAll()).thenReturn(patients);

        // Act
        List<Patient> result = patientenService.getAllPatients();

        // Assert
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Doe", result.get(1).getName());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void testDeletePatientById() {
        // Arrange
        Long patientId = 1L;

        // Act
        patientenService.deletePatientById(patientId);

        // Assert
        verify(patientRepository, times(1)).deletePatientById(patientId);
    }

    @Test
    void testGetPatientByUsername() {
        // Arrange
        String username = "patientUser";
        Patient patient = new Patient(112L, "Jane Doe", false, "patient@example.com", "01-01-1990", "123 Street", username, "password456");
        when(patientRepository.findByUsername(username)).thenReturn(Optional.of(patient));

        // Act
        Optional<Patient> result = patientenService.getPatientByUsername(username);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Jane Doe", result.get().getName());
        verify(patientRepository, times(1)).findByUsername(username);
    }

    @Test
    void testGetPatientByEmail() {
        // Arrange
        String email = "patient@example.com";
        Patient patient = new Patient(112L, "Jane Doe", false, email, "01-01-1990", "123 Street", "patientUser", "password456");
        when(patientRepository.findByEmail(email)).thenReturn(Optional.of(patient));

        // Act
        Optional<Patient> result = patientenService.getPatientByEmail(email);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Jane Doe", result.get().getName());
        verify(patientRepository, times(1)).findByEmail(email);
    }

    @Test
    void testGenerateRandomId() {
        // Act
        Long generatedId = patientenService.generateRandomId();

        // Assert
        assertNotNull(generatedId);
        assertTrue(generatedId >= 1000000000L && generatedId < 10000000000L); // Überprüfen, ob die ID im gültigen Bereich liegt
    }
}
