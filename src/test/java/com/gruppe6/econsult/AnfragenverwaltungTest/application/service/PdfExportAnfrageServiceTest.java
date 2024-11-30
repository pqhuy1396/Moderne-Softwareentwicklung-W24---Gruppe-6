package com.gruppe6.econsult.AnfragenverwaltungTest.application.service;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.gruppe6.econsult.Anfragenverwaltung.application.service.AnfrageService;
import com.gruppe6.econsult.Anfragenverwaltung.application.service.PdfExportAnfrageService;
import com.gruppe6.econsult.Anfragenverwaltung.domain.model.Anfrage;
import com.gruppe6.econsult.Arztverwaltung.application.service.ArztService;
import com.gruppe6.econsult.Arztverwaltung.domain.model.Arzt;
import com.gruppe6.econsult.Patientenverwaltung.application.service.PatientenService;
import com.gruppe6.econsult.Patientenverwaltung.domain.model.Patient;

class PdfExportAnfrageServiceTest {

    @Mock
    private AnfrageService anfrageService;

    @Mock
    private ArztService arztService;

    @Mock
    private PatientenService patientenService;

    @InjectMocks
    private PdfExportAnfrageService pdfExportAnfrageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void exportAnfrageToPdf_shouldReturnPdfByteArray() throws IOException {
        // Arrange
        Long anfrageId = 1L;

        Anfrage mockAnfrage = new Anfrage(new Date(), "foto.jpg", "Anfrage Beschreibung", false, 1L, 2L);
        mockAnfrage.setId(anfrageId);

        Arzt mockArzt = new Arzt();
        mockArzt.setId(2L);
        mockArzt.setName("Dr. Max Mustermann");
        mockArzt.setFachrichtung("Allgemeinmedizin");
        mockArzt.setLizenznummer("LZ12345");

        Patient mockPatient = new Patient();
        mockPatient.setId(1L);
        mockPatient.setName("Anna Müller");
        mockPatient.setEmail("anna.mueller@example.com");
        mockPatient.setBirthDate("22.10.1996");

        when(anfrageService.getAnfrageById(anfrageId)).thenReturn(Optional.of(mockAnfrage));
        when(arztService.getArztById(2L)).thenReturn(Optional.of(mockArzt));
        when(patientenService.getPatientById(1L)).thenReturn(Optional.of(mockPatient));

        // Act
        byte[] result = pdfExportAnfrageService.exportAnfrageToPdf(anfrageId);

        // Assert
        assertNotNull(result);
        assertTrue(result.length > 0); // Prüft, ob die Byte-Arrays nicht leer ist
        verify(anfrageService, times(1)).getAnfrageById(anfrageId);
        verify(arztService, times(1)).getArztById(2L);
        verify(patientenService, times(1)).getPatientById(1L);
    }

    @Test
    void exportAnfrageToPdf_shouldThrowExceptionWhenAnfrageNotFound() {
        // Arrange
        Long anfrageId = 1L;

        when(anfrageService.getAnfrageById(anfrageId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> pdfExportAnfrageService.exportAnfrageToPdf(anfrageId));

        assertEquals("Anfrage not found with ID: " + anfrageId, exception.getMessage());
        verify(anfrageService, times(1)).getAnfrageById(anfrageId);
        verify(arztService, never()).getArztById(any());
        verify(patientenService, never()).getPatientById(any());
    }

    @Test
    void exportAnfrageToPdf_shouldThrowExceptionWhenArztOrPatientNotFound() {
        // Arrange
        Long anfrageId = 1L;

        Anfrage mockAnfrage = new Anfrage(new Date(), "foto.jpg", "Anfrage Beschreibung", false, 1L, 2L);
        mockAnfrage.setId(anfrageId);

        when(anfrageService.getAnfrageById(anfrageId)).thenReturn(Optional.of(mockAnfrage));
        when(arztService.getArztById(2L)).thenReturn(Optional.empty()); // Arzt nicht gefunden

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> pdfExportAnfrageService.exportAnfrageToPdf(anfrageId));

        assertEquals("Arzt or Patient not found for the Anfrage.", exception.getMessage());
        verify(anfrageService, times(1)).getAnfrageById(anfrageId);
        verify(arztService, times(1)).getArztById(2L);
        //verify(patientenService, never()).getPatientById(any());
    }
}
