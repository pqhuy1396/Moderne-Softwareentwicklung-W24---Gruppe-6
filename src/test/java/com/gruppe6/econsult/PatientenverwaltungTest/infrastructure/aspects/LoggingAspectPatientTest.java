package com.gruppe6.econsult.PatientenverwaltungTest.infrastructure.aspects;

import java.util.Optional;

import com.gruppe6.econsult.Patientenverwaltung.domain.events.PatientController;
import com.gruppe6.econsult.Patientenverwaltung.domain.model.Patient;
import com.gruppe6.econsult.Patientenverwaltung.infrastructure.aspects.LoggingAspectPatient;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.gruppe6.econsult.Patientenverwaltung.application.service.PatientenService;

@SpringBootTest
class LoggingAspectPatientTest {

    @Autowired
    private PatientController patientController; 

    @MockBean
    private PatientenService mockPatientController; 

    @Test
    void testLogBefore() {
        // Arrange
        Patient patient = new Patient(1L, "John Doe", false, "email@example.com", "1990-01-01", "123 Street", "johndoe", "password");
        when(mockPatientController.getPatientById(1L)).thenReturn(Optional.of(patient));

        // Act
        ResponseEntity<Patient> response = patientController.getPatientById(1L);

        // Assert
        verify(mockPatientController).getPatientById(1L);
        assertEquals(patient, response.getBody());
    }

    @Test
    void testLogAfterReturning() {
        // Arrange
        Patient patient = new Patient(1L, "John Doe", false, "email@example.com", "1990-01-01", "123 Street", "johndoe", "password");
        when(mockPatientController.getPatientById(1L)).thenReturn(Optional.of(patient));

        // Act
        ResponseEntity<Patient> response = patientController.getPatientById(1L);

        // Assert
        verify(mockPatientController).getPatientById(1L);
        assertEquals(patient, response.getBody());
    }

    @Test
    void testLogAfterThrowing() {
        // Arrange
        when(mockPatientController.getPatientById(1L)).thenThrow(new RuntimeException("Test Exception"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> patientController.getPatientById(1L));
        verify(mockPatientController).getPatientById(1L);
    }
}
