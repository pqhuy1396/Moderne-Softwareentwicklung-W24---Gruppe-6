package com.gruppe6.econsult.AbrechnungverwaltungTest.Application.service;

import com.gruppe6.econsult.Abrechnungverwaltung.application.service.RechnungService;
import com.gruppe6.econsult.Abrechnungverwaltung.domain.model.Rechnung;
import com.gruppe6.econsult.Abrechnungverwaltung.infrastructure.reponsitory.RechnungRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RechnungServiceTest {

    @Mock
    private RechnungRepository rechnungRepository;

    @InjectMocks
    private RechnungService rechnungService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRechnung_shouldSaveAndReturnRechnung() {
        // Arrange
        Long patientId = 1L;
        String rechnungNummer = UUID.randomUUID().toString();
        Rechnung mockRechnung = new Rechnung(rechnungNummer, patientId);
        mockRechnung.setDatum(new Date());
        mockRechnung.setBetrag(20);

        when(rechnungRepository.save(any(Rechnung.class))).thenReturn(mockRechnung);

        // Act
        Rechnung result = rechnungService.createRechnung(patientId);

        // Assert
        assertNotNull(result);
        assertEquals(patientId, result.getIdPatient());
        assertEquals(20, result.getBetrag());
        verify(rechnungRepository, times(1)).save(any(Rechnung.class));
    }

    @Test
    void getRechnungenForPatient_shouldReturnListOfRechnungen() {
        // Arrange
        Long patientId = 1L;
        Rechnung rechnung1 = new Rechnung("123-abc", patientId);
        rechnung1.setDatum(new Date());
        rechnung1.setBetrag(20);

        Rechnung rechnung2 = new Rechnung("456-def", patientId);
        rechnung2.setDatum(new Date());
        rechnung2.setBetrag(20);

        List<Rechnung> mockRechnungen = Arrays.asList(rechnung1, rechnung2);

        when(rechnungRepository.findByIdPatient(patientId)).thenReturn(mockRechnungen);

        // Act
        List<Rechnung> result = rechnungService.getRechnungenForPatient(patientId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(rechnungRepository, times(1)).findByIdPatient(patientId);
    }

    @Test
    void getRechnungById_shouldReturnRechnungWhenFound() {
        // Arrange
        Long rechnungId = 1L;
        Rechnung mockRechnung = new Rechnung("123-abc", 1L);
        mockRechnung.setId(rechnungId);
        mockRechnung.setDatum(new Date());
        mockRechnung.setBetrag(20);

        when(rechnungRepository.findById(rechnungId)).thenReturn(Optional.of(mockRechnung));

        // Act
        Optional<Rechnung> result = rechnungService.getRechnungById(rechnungId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(rechnungId, result.get().getId());
        verify(rechnungRepository, times(1)).findById(rechnungId);
    }

    @Test
    void getRechnungById_shouldReturnEmptyWhenNotFound() {
        // Arrange
        Long rechnungId = 1L;

        when(rechnungRepository.findById(rechnungId)).thenReturn(Optional.empty());

        // Act
        Optional<Rechnung> result = rechnungService.getRechnungById(rechnungId);

        // Assert
        assertFalse(result.isPresent());
        verify(rechnungRepository, times(1)).findById(rechnungId);
    }

    @Test
    void deleteRechnung_shouldDeleteRechnung() {
        // Arrange
        Long rechnungId = 1L;

        // Act
        rechnungService.deleteRechnung(rechnungId);

        // Assert
        verify(rechnungRepository, times(1)).deleteById(rechnungId);
    }
}
