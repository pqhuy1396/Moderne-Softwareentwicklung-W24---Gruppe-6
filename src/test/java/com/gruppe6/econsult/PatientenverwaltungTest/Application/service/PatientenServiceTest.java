package com.gruppe6.econsult.PatientenverwaltungTest.Application.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gruppe6.econsult.Patientenverwaltung.application.service.PatientenService;
import com.gruppe6.econsult.Patientenverwaltung.domain.model.Patient;
import com.gruppe6.econsult.Patientenverwaltung.infrastructure.repository.PatientRepository;

@ExtendWith(MockitoExtension.class)
class PatientenServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientenService patientenService;

    @Test
    void getAllPatients_shouldReturnAllPatients() {
        // Arrange
        List<Patient> patientList = Arrays.asList(
                new Patient(1L, "Max Mustermann", false, "mustermann@example.com", "1990-01-01", "123 Main St", "max_muster", "password"),
                new Patient(2L, "Jane Doe", false, "jane.doe@example.com", "1985-05-20", "456 Elm St", "jane_doe", "password123")
        );

        when(patientRepository.findAll()).thenReturn(patientList);

        // Act
        List<Patient> result = patientenService.getAllPatients();

        // Assert
        assertEquals(2, result.size(), "Es sollten zwei Patienten zurückgegeben werden");
        assertEquals("Max Mustermann", result.get(0).getName(), "Name des ersten Patienten sollte übereinstimmen");
        assertEquals("Jane Doe", result.get(1).getName(), "Name des zweiten Patienten sollte übereinstimmen");
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void getPatientById_shouldReturnPatientWhenFound() {
        // Arrange
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setId(patientId);

        when(patientRepository.findPatientById(patientId)).thenReturn(Optional.of(patient));

        // Act
        Optional<Patient> result = patientenService.getPatientById(patientId);

        // Assert
        assertTrue(result.isPresent(), "Patient sollte vorhanden sein");
        assertEquals(patientId, result.get().getId(), "Patienten-ID sollte übereinstimmen");
        verify(patientRepository, times(1)).findPatientById(patientId);
    }

    @Test
    void getPatientById_shouldReturnEmptyWhenNotFound() {
        // Arrange
        Long patientId = 1L;

        when(patientRepository.findPatientById(patientId)).thenReturn(Optional.empty());

        // Act
        Optional<Patient> result = patientenService.getPatientById(patientId);

        // Assert
        assertFalse(result.isPresent(), "Patient sollte nicht gefunden werden");
        verify(patientRepository, times(1)).findPatientById(patientId);
    }

    @Test
    void savePatient_shouldSaveAndReturnPatient() {
        // Arrange
        Patient patient = new Patient();
        patient.setName("Max Mustermann");

        when(patientRepository.save(patient)).thenReturn(patient);

        // Act
        Patient result = patientenService.savePatient(patient);

        // Assert
        assertNotNull(result, "Das gespeicherte Patient-Objekt sollte nicht null sein");
        assertEquals("Max Mustermann", result.getName(), "Der Name des Patienten sollte übereinstimmen");
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    void deletePatientById_shouldInvokeRepositoryDelete() {
        // Arrange
        Long patientId = 1L;

        // Act
        patientenService.deletePatientById(patientId);

        // Assert
        verify(patientRepository, times(1)).deletePatientById(patientId);
    }

    @Test
    void getPatientByUsername_shouldReturnPatientWhenFound() {
        // Arrange
        String username = "max_muster";
        Patient patient = new Patient();
        patient.setUsername(username);

        when(patientRepository.findByUsername(username)).thenReturn(Optional.of(patient));

        // Act
        Optional<Patient> result = patientenService.getPatientByUsername(username);

        // Assert
        assertTrue(result.isPresent(), "Patient sollte vorhanden sein");
        assertEquals(username, result.get().getUsername(), "Der Benutzername sollte übereinstimmen");
        verify(patientRepository, times(1)).findByUsername(username);
    }

    @Test
    void getPatientByUsername_shouldReturnEmptyWhenNotFound() {
        // Arrange
        String username = "max_muster";

        when(patientRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        Optional<Patient> result = patientenService.getPatientByUsername(username);

        // Assert
        assertFalse(result.isPresent(), "Patient sollte nicht gefunden werden");
        verify(patientRepository, times(1)).findByUsername(username);
    }

    @Test
    void getPatientByEmail_shouldReturnPatientWhenFound() {
        // Arrange
        String email = "mustermann@example.com";
        Patient patient = new Patient();
        patient.setEmail(email);

        when(patientRepository.findByEmail(email)).thenReturn(Optional.of(patient));

        // Act
        Optional<Patient> result = patientenService.getPatientByEmail(email);

        // Assert
        assertTrue(result.isPresent(), "Patient sollte vorhanden sein");
        assertEquals(email, result.get().getEmail(), "Die E-Mail sollte übereinstimmen");
        verify(patientRepository, times(1)).findByEmail(email);
    }

    @Test
    void getPatientByEmail_shouldReturnEmptyWhenNotFound() {
        // Arrange
        String email = "mustermann@example.com";

        when(patientRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        Optional<Patient> result = patientenService.getPatientByEmail(email);

        // Assert
        assertFalse(result.isPresent(), "Patient sollte nicht gefunden werden");
        verify(patientRepository, times(1)).findByEmail(email);
    }

}
