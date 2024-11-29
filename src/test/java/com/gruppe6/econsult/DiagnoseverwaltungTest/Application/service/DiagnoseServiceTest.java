package com.gruppe6.econsult.DiagnoseverwaltungTest.Application.service;

import com.gruppe6.econsult.Anfragenverwaltung.domain.model.Anfrage;
import com.gruppe6.econsult.Anfragenverwaltung.infrastructure.repository.AnfrageRepository;
import com.gruppe6.econsult.Diagnoseverwaltung.application.service.DiagnoseService;
import com.gruppe6.econsult.Diagnoseverwaltung.domain.model.Diagnose;
import com.gruppe6.econsult.Diagnoseverwaltung.infrastructure.repository.DiagnoseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiagnoseServiceTest {

    @Mock
    private DiagnoseRepository diagnoseRepository;

    @Mock
    private AnfrageRepository anfrageRepository;

    @InjectMocks
    private DiagnoseService diagnoseService;

    @Test
    void createDiagnose_shouldSaveAndReturnDiagnose() {
        // Arrange
        Long anfrageId = 1L;
        String diagnoseText = "Grippe diagnostiziert";
        Anfrage mockAnfrage = new Anfrage();
        mockAnfrage.setId(anfrageId);
        mockAnfrage.setArztId(101L);

        Diagnose mockDiagnose = new Diagnose(mockAnfrage, diagnoseText, new Date(), mockAnfrage.getArztId());

        when(anfrageRepository.findById(anfrageId)).thenReturn(Optional.of(mockAnfrage));
        when(diagnoseRepository.save(any(Diagnose.class))).thenReturn(mockDiagnose);

        // Act
        Diagnose result = diagnoseService.createDiagnose(anfrageId, diagnoseText);

        // Assert
        assertNotNull(result);
        assertEquals(diagnoseText, result.getDiagnoseText());
        assertEquals(mockAnfrage, result.getAnfrage());
        verify(anfrageRepository, times(1)).findById(anfrageId);
        verify(diagnoseRepository, times(1)).save(any(Diagnose.class));
    }

    @Test
    void createDiagnose_shouldThrowExceptionWhenAnfrageNotFound() {
        // Arrange
        Long anfrageId = 1L;
        String diagnoseText = "Grippe diagnostiziert";

        when(anfrageRepository.findById(anfrageId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> diagnoseService.createDiagnose(anfrageId, diagnoseText));
        assertEquals("Anfrage nicht gefunden mit ID: " + anfrageId, exception.getMessage());
        verify(anfrageRepository, times(1)).findById(anfrageId);
        verify(diagnoseRepository, never()).save(any(Diagnose.class));
    }

    @Test
    void getDiagnoseByAnfrageId_shouldReturnDiagnoseWhenFound() {
        // Arrange
        Long anfrageId = 1L;
        Diagnose mockDiagnose = new Diagnose(new Anfrage(), "Grippe diagnostiziert", new Date(), 101L);

        when(diagnoseRepository.findByAnfrageId(anfrageId)).thenReturn(Optional.of(mockDiagnose));

        // Act
        Diagnose result = diagnoseService.getDiagnoseByAnfrageId(anfrageId);

        // Assert
        assertNotNull(result);
        assertEquals("Grippe diagnostiziert", result.getDiagnoseText());
        verify(diagnoseRepository, times(1)).findByAnfrageId(anfrageId);
    }

    @Test
    void getDiagnoseByAnfrageId_shouldReturnNullWhenNotFound() {
        // Arrange
        Long anfrageId = 1L;

        when(diagnoseRepository.findByAnfrageId(anfrageId)).thenReturn(Optional.empty());

        // Act
        Diagnose result = diagnoseService.getDiagnoseByAnfrageId(anfrageId);

        // Assert
        assertNull(result);
        verify(diagnoseRepository, times(1)).findByAnfrageId(anfrageId);
    }

    @Test
    void getDiagnosenForArzt_shouldReturnListOfDiagnosen() {
        // Arrange
        Long arztId = 101L;
        Diagnose diagnose1 = new Diagnose(new Anfrage(), "Grippe", new Date(), arztId);
        Diagnose diagnose2 = new Diagnose(new Anfrage(), "Fieber", new Date(), arztId);

        List<Diagnose> mockDiagnosen = Arrays.asList(diagnose1, diagnose2);

        when(diagnoseRepository.findByArztId(arztId)).thenReturn(mockDiagnosen);

        // Act
        List<Diagnose> result = diagnoseService.getDiagnosenForArzt(arztId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(diagnoseRepository, times(1)).findByArztId(arztId);
    }
}
