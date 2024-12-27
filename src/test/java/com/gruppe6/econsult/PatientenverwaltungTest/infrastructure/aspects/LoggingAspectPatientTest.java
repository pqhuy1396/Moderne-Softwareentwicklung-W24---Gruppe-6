package com.gruppe6.econsult.PatientenverwaltungTest.infrastructure.aspects;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.gruppe6.econsult.Patientenverwaltung.application.service.PatientenService;
import com.gruppe6.econsult.Patientenverwaltung.domain.events.PatientGet;
import com.gruppe6.econsult.Patientenverwaltung.domain.events.PatientLogin;
import com.gruppe6.econsult.Patientenverwaltung.domain.events.PatientRegister;
import com.gruppe6.econsult.Patientenverwaltung.domain.model.Patient;

@SpringBootTest
class LoggingAspectPatientTest {

    @Autowired
    private PatientGet patientGetController;

    @Autowired
    private PatientLogin patientLoginController;

    @Autowired
    private PatientRegister patientRegisterController;

    @MockBean
    private PatientenService mockPatientenService;

    @Test
    void testLogBeforeGetPatientById() {
        Patient patient = new Patient(1L, "John Doe", false, "email@example.com", "1990-01-01", "123 Street", "johndoe", "password");
        when(mockPatientenService.getPatientById(1L)).thenReturn(Optional.of(patient));

        ResponseEntity<Patient> response = patientGetController.getPatientById(1L);

        verify(mockPatientenService).getPatientById(1L);
        assertEquals(patient, response.getBody());
    }

    @Test
    void testLogBeforeLogin() {
        Patient patient = new Patient(1L, "John Doe", false, "email@example.com", "1990-01-01", "123 Street", "johndoe", "password");
        when(mockPatientenService.getPatientByUsername("johndoe")).thenReturn(Optional.of(patient));

        ResponseEntity<Patient> response = patientLoginController.login("johndoe", "password");

        verify(mockPatientenService).getPatientByUsername("johndoe");
        assertEquals(patient, response.getBody());
    }

    @Test
    void testLogBeforeRegisterPatient() {
        when(mockPatientenService.generateRandomId()).thenReturn(1L);

        ResponseEntity<String> response = patientRegisterController.register("John Doe", "email@example.com", "johndoe", "password", "123 Street", "1990-01-01");

        verify(mockPatientenService).savePatient(any(Patient.class));
        assertEquals("Patient registered successfully", response.getBody());
    }
}
