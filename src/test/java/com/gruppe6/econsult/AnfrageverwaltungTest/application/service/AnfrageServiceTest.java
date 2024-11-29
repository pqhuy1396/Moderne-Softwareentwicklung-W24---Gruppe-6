package com.gruppe6.econsult.AnfrageverwaltungTest.application.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.gruppe6.econsult.Anfragenverwaltung.application.service.AnfrageService;
import com.gruppe6.econsult.Anfragenverwaltung.domain.model.Anfrage;
import com.gruppe6.econsult.Anfragenverwaltung.infrastructure.repository.AnfrageRepository;


class AnfrageServiceTest {

    @Mock
    private AnfrageRepository anfrageRepository;

    @InjectMocks
    private AnfrageService anfrageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAnfrage_case1() {
        Long patientId = 1L;
        Long arztId = 2L;
        String foto = "test.jpg";
        String beschreibung = "Test description";

        Anfrage anfrage = new Anfrage(new Date(), foto, beschreibung, false, patientId, arztId);
        when(anfrageRepository.save(any(Anfrage.class))).thenReturn(anfrage);

        Anfrage result = anfrageService.createAnfrage(patientId, arztId, foto, beschreibung);

        assertNotNull(result);
        assertEquals(foto, result.getFoto());
        assertEquals(beschreibung, result.getBeschreibung());
        assertEquals(patientId, result.getPatientId());
        assertEquals(arztId, result.getArztId());
        verify(anfrageRepository, times(1)).save(any(Anfrage.class));
    }

    @Test
    void getAnfragenForPatient_case1() {
        Long patientId = 1L;
        List<Anfrage> anfragen = List.of(new Anfrage(new Date(), "test.jpg", "Description", false, patientId, 2L));
        when(anfrageRepository.findAnfragenByPatientId(patientId)).thenReturn(anfragen);

        List<Anfrage> result = anfrageService.getAnfragenForPatient(patientId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(patientId, result.get(0).getPatientId());
        verify(anfrageRepository, times(1)).findAnfragenByPatientId(patientId);
    }

    @Test
    void getAnfragenForArzt_case1() {
        Long arztId = 2L;
        List<Anfrage> anfragen = List.of(new Anfrage(new Date(), "test.jpg", "Description", false, 1L, arztId));
        when(anfrageRepository.findAnfragenByArztId(arztId)).thenReturn(anfragen);

        List<Anfrage> result = anfrageService.getAnfragenForArzt(arztId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(arztId, result.get(0).getArztId());
        verify(anfrageRepository, times(1)).findAnfragenByArztId(arztId);
    }

    @Test
    void completeAnfrage_case1() {
        Long anfrageId = 1L;
        Anfrage anfrage = new Anfrage(new Date(), "test.jpg", "Description", false, 1L, 2L);
        when(anfrageRepository.findById(anfrageId)).thenReturn(Optional.of(anfrage));
        when(anfrageRepository.save(anfrage)).thenReturn(anfrage);

        Anfrage result = anfrageService.completeAnfrage(anfrageId);

        assertTrue(result.getStatus());
        verify(anfrageRepository, times(1)).findById(anfrageId);
        verify(anfrageRepository, times(1)).save(anfrage);
    }

    @Test
    void completeAnfrage_case2() {
        Long anfrageId = 1L;
        when(anfrageRepository.findById(anfrageId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            anfrageService.completeAnfrage(anfrageId);
        });

        assertEquals("Anfrage not found with ID: " + anfrageId, exception.getMessage());
        verify(anfrageRepository, times(1)).findById(anfrageId);
        verify(anfrageRepository, times(0)).save(any(Anfrage.class));
    }
}
