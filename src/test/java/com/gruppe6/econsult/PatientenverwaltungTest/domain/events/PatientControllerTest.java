package com.gruppe6.econsult.PatientenverwaltungTest.domain.events;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


import com.gruppe6.econsult.Patientenverwaltung.application.service.PatientenService;
import com.gruppe6.econsult.Patientenverwaltung.domain.events.PatientController;
import com.gruppe6.econsult.Patientenverwaltung.domain.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


class PatientControllerTest {

    @Mock
    private PatientenService patientenService;

    @InjectMocks
    private PatientController patientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPatientById_case1() {
        Patient patient = new Patient(1L, "John Doe", false, "john.doe@example.com", "1990-01-01",
        "123 Main St", "johndoe", "password");
        when(patientenService.getPatientById(1L)).thenReturn(Optional.of(patient));

        ResponseEntity<Patient> response = patientController.getPatientById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patient, response.getBody());
    }

    @Test
    void getPatientById_case2() {
        when(patientenService.getPatientById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Patient> response = patientController.getPatientById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
}
