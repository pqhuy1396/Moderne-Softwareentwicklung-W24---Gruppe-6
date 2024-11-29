package com.gruppe6.econsult.AnfragenverwaltungTest.application.service;

import com.gruppe6.econsult.Anfragenverwaltung.application.service.AnfrageService;
import com.gruppe6.econsult.Anfragenverwaltung.domain.model.Anfrage;
import com.gruppe6.econsult.Anfragenverwaltung.infrastructure.repository.AnfrageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void createAnfrage_shouldSaveAndReturnAnfrage() {
        // Arrange
        Long patientId = 1L;
        Long arztId = 2L;
        String foto = "sample.jpg";
        String beschreibung = "Anfrage Beschreibung";
        Anfrage mockAnfrage = new Anfrage(new Date(), foto, beschreibung, false, patientId, arztId);

        when(anfrageRepository.save(any(Anfrage.class))).thenReturn(mockAnfrage);

        // Act
        Anfrage result = anfrageService.createAnfrage(patientId, arztId, foto, beschreibung);

        // Assert
        assertNotNull(result);
        assertEquals(patientId, result.getPatientId());
        assertEquals(arztId, result.getArztId());
        assertEquals(beschreibung, result.getBeschreibung());
        verify(anfrageRepository, times(1)).save(any(Anfrage.class));
    }

    @Test
    void getAnfragenForPatient_shouldReturnListOfAnfragen() {
        // Arrange
        Long patientId = 1L;
        Anfrage anfrage1 = new Anfrage(new Date(), "foto1.jpg", "Anfrage 1", false, patientId, 2L);
        Anfrage anfrage2 = new Anfrage(new Date(), "foto2.jpg", "Anfrage 2", false, patientId, 3L);

        List<Anfrage> mockAnfragen = Arrays.asList(anfrage1, anfrage2);

        when(anfrageRepository.findAnfragenByPatientId(patientId)).thenReturn(mockAnfragen);

        // Act
        List<Anfrage> result = anfrageService.getAnfragenForPatient(patientId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(anfrageRepository, times(1)).findAnfragenByPatientId(patientId);
    }

    @Test
    void getAnfragenForArzt_shouldReturnListOfAnfragen() {
        // Arrange
        Long arztId = 2L;
        Anfrage anfrage1 = new Anfrage(new Date(), "foto1.jpg", "Anfrage 1", false, 1L, arztId);
        Anfrage anfrage2 = new Anfrage(new Date(), "foto2.jpg", "Anfrage 2", false, 3L, arztId);

        List<Anfrage> mockAnfragen = Arrays.asList(anfrage1, anfrage2);

        when(anfrageRepository.findAnfragenByArztId(arztId)).thenReturn(mockAnfragen);

        // Act
        List<Anfrage> result = anfrageService.getAnfragenForArzt(arztId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(anfrageRepository, times(1)).findAnfragenByArztId(arztId);
    }

    @Test
    void getAnfrageById_shouldReturnAnfrageWhenFound() {
        // Arrange
        Long anfrageId = 1L;
        Anfrage mockAnfrage = new Anfrage(new Date(), "foto.jpg", "Anfrage Beschreibung", false, 1L, 2L);

        when(anfrageRepository.findById(anfrageId)).thenReturn(Optional.of(mockAnfrage));

        // Act
        Optional<Anfrage> result = anfrageService.getAnfrageById(anfrageId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Anfrage Beschreibung", result.get().getBeschreibung());
        verify(anfrageRepository, times(1)).findById(anfrageId);
    }

    @Test
    void getAnfrageById_shouldReturnEmptyWhenNotFound() {
        // Arrange
        Long anfrageId = 1L;

        when(anfrageRepository.findById(anfrageId)).thenReturn(Optional.empty());

        // Act
        Optional<Anfrage> result = anfrageService.getAnfrageById(anfrageId);

        // Assert
        assertFalse(result.isPresent());
        verify(anfrageRepository, times(1)).findById(anfrageId);
    }

    @Test
    void completeAnfrage_shouldMarkAnfrageAsCompleted() {
        // Arrange
        Long anfrageId = 1L;
        Anfrage mockAnfrage = new Anfrage(new Date(), "foto.jpg", "Anfrage Beschreibung", false, 1L, 2L);
        mockAnfrage.setId(anfrageId);

        when(anfrageRepository.findById(anfrageId)).thenReturn(Optional.of(mockAnfrage));
        when(anfrageRepository.save(any(Anfrage.class))).thenReturn(mockAnfrage);

        // Act
        Anfrage result = anfrageService.completeAnfrage(anfrageId);

        // Assert
        assertNotNull(result);
        assertTrue(result.getStatus());
        verify(anfrageRepository, times(1)).findById(anfrageId);
        verify(anfrageRepository, times(1)).save(any(Anfrage.class));
    }

    @Test
    void completeAnfrage_shouldThrowExceptionWhenNotFound() {
        // Arrange
        Long anfrageId = 1L;

        when(anfrageRepository.findById(anfrageId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> anfrageService.completeAnfrage(anfrageId));
        assertEquals("Anfrage not found with ID: " + anfrageId, exception.getMessage());
        verify(anfrageRepository, times(1)).findById(anfrageId);
        verify(anfrageRepository, never()).save(any(Anfrage.class));
    }
}
