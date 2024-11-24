package com.gruppe6.econsult.PatientenverwaltungTest.Service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gruppe6.econsult.Patientenverwaltung.Entity.Patient;
import com.gruppe6.econsult.Patientenverwaltung.Repository.PatientRepository;
import com.gruppe6.econsult.Patientenverwaltung.Service.PatientenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;


class PatientenServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientenService patientenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPatientById() {
        Patient patient = new Patient(112L,"Jane Doe", false, "patient@example.com", "01-01-1990", "123 Street", "patientUser", "password456");
        when(patientRepository.findPatientById(1L)).thenReturn(Optional.of(patient));

        Optional<Patient> result = patientenService.getPatientById(1L);

        assertEquals("Jane Doe", result.get().getName());
        verify(patientRepository, times(1)).findPatientById(1L);
    }

    @Test
    void testSavePatient() {
        Patient patient = new Patient(112L,"Jane Doe", false, "patient@example.com", "01-01-1990", "123 Street", "patientUser", "password456");
        when(patientRepository.save(patient)).thenReturn(patient);

        Patient result = patientenService.savePatient(patient);

        assertEquals("Jane Doe", result.getName());
        verify(patientRepository, times(1)).save(patient);
    }
}
