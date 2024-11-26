package com.gruppe6.econsult.AnfrageverwaltungTest.domain.events;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gruppe6.econsult.Anfragenverwaltung.application.service.AnfrageService;
import com.gruppe6.econsult.Anfragenverwaltung.application.service.PdfExportAnfrageService;
import com.gruppe6.econsult.Anfragenverwaltung.domain.events.AnfrageController;
import com.gruppe6.econsult.Anfragenverwaltung.domain.model.Anfrage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;





class AnfrageControllerTest {

    @Mock
    private AnfrageService anfrageService;

    @Mock
    private PdfExportAnfrageService pdfExportAnfrageService;

    @InjectMocks
    private AnfrageController anfrageController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test for createAnfrage
    @Test
    void createAnfrage_case1() {
        // Test case 1: Successful creation
        Anfrage anfrage = new Anfrage();
        when(anfrageService.createAnfrage(1L, 2L, "photo.jpg", "description")).thenReturn(anfrage);

        ResponseEntity<Anfrage> response = anfrageController.createAnfrage(1L, 2L, "photo.jpg", "description");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(anfrage, response.getBody());
    }

    @Test
    void createAnfrage_case2() {
        // Test case 2: Missing parameter results in BAD_REQUEST
        when(anfrageService.createAnfrage(null, 2L, "photo.jpg", "description")).thenThrow(IllegalArgumentException.class);

        ResponseEntity<Anfrage> response = anfrageController.createAnfrage(null, 2L, "photo.jpg", "description");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void createAnfrage_case3() {
        // Test case 3: IllegalArgumentException handled
        when(anfrageService.createAnfrage(1L, 2L, "photo.jpg", "description")).thenThrow(IllegalArgumentException.class);

        ResponseEntity<Anfrage> response = anfrageController.createAnfrage(1L, 2L, "photo.jpg", "description");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // Test for getAnfragenForArzt
    @Test
    void getAnfragenForArzt_case1() {
        // Test case 1: Arzt with requests
        List<Anfrage> anfragen = Arrays.asList(new Anfrage(), new Anfrage());
        when(anfrageService.getAnfragenForArzt(1L)).thenReturn(anfragen);

        ResponseEntity<List<Anfrage>> response = anfrageController.getAnfragenForArzt(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(anfragen, response.getBody());
    }

    @Test
    void getAnfragenForArzt_case2() {
        // Test case 2: Arzt with no requests
        when(anfrageService.getAnfragenForArzt(1L)).thenReturn(Arrays.asList());

        ResponseEntity<List<Anfrage>> response = anfrageController.getAnfragenForArzt(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void getAnfragenForArzt_case3() {
        // Test case 3: Invalid Arzt ID
        when(anfrageService.getAnfragenForArzt(99L)).thenReturn(null);

        ResponseEntity<List<Anfrage>> response = anfrageController.getAnfragenForArzt(99L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    // Test for getAnfragenForPatient
    @Test
    void getAnfragenForPatient_case1() {
        // Test case 1: Patient with requests
        List<Anfrage> anfragen = Arrays.asList(new Anfrage(), new Anfrage());
        when(anfrageService.getAnfragenForPatient(1L)).thenReturn(anfragen);

        ResponseEntity<List<Anfrage>> response = anfrageController.getAnfragenForPatient(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(anfragen, response.getBody());
    }

    @Test
    void getAnfragenForPatient_case2() {
        // Test case 2: Patient with no requests
        when(anfrageService.getAnfragenForPatient(1L)).thenReturn(Arrays.asList());

        ResponseEntity<List<Anfrage>> response = anfrageController.getAnfragenForPatient(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void getAnfragenForPatient_case3() {
        // Test case 3: Invalid Patient ID
        when(anfrageService.getAnfragenForPatient(99L)).thenReturn(null);

        ResponseEntity<List<Anfrage>> response = anfrageController.getAnfragenForPatient(99L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    // Test for exportAnfrageToPdf
    @Test
    void exportAnfrageToPdf_case1() throws IOException {
        // Test case 1: Successful PDF export
        byte[] pdfData = new byte[]{1, 2, 3};
        when(pdfExportAnfrageService.exportAnfrageToPdf(1L)).thenReturn(pdfData);

        ResponseEntity<byte[]> response = anfrageController.exportAnfrageToPdf(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pdfData, response.getBody());
    }

    @Test
    void exportAnfrageToPdf_case2() throws IOException {
        // Test case 2: PDF not found or IO exception
        when(pdfExportAnfrageService.exportAnfrageToPdf(99L)).thenThrow(IOException.class);

        ResponseEntity<byte[]> response = anfrageController.exportAnfrageToPdf(99L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void exportAnfrageToPdf_case3() throws IOException {
        // Test case 3: Invalid Anfrage ID
        when(pdfExportAnfrageService.exportAnfrageToPdf(null)).thenReturn(null);

        ResponseEntity<byte[]> response = anfrageController.exportAnfrageToPdf(null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Test for completeAnfrage
    @Test
    void completeAnfrage_case1() {
        // Test case 1: Successfully completes the Anfrage
        Anfrage anfrage = new Anfrage();
        when(anfrageService.completeAnfrage(1L)).thenReturn(anfrage);

        ResponseEntity<Anfrage> response = anfrageController.completeAnfrage(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(anfrage, response.getBody());
    }

    @Test
    void completeAnfrage_case2() {
        // Test case 2: Anfrage not found (throws IllegalArgumentException)
        when(anfrageService.completeAnfrage(99L)).thenThrow(IllegalArgumentException.class);

        ResponseEntity<Anfrage> response = anfrageController.completeAnfrage(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void completeAnfrage_case3() {
        // Test case 3: Invalid Anfrage ID provided
        when(anfrageService.completeAnfrage(null)).thenReturn(null);

        ResponseEntity<Anfrage> response = anfrageController.completeAnfrage(null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
