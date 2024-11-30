package com.gruppe6.econsult.AnfragenverwaltungTest.domain.events;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gruppe6.econsult.Anfragenverwaltung.application.service.AnfrageService;
import com.gruppe6.econsult.Anfragenverwaltung.application.service.PdfExportAnfrageService;
import com.gruppe6.econsult.Anfragenverwaltung.domain.events.AnfrageController;
import com.gruppe6.econsult.Anfragenverwaltung.domain.model.Anfrage;

@WebMvcTest(AnfrageController.class)
class AnfrageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnfrageService anfrageService;

    @MockBean
    private PdfExportAnfrageService pdfExportAnfrageService;

    @Test
    void createAnfrage_shouldReturnCreatedAnfrage() throws Exception {
        // Arrange
        Long patientId = 1L;
        Long arztId = 2L;
        String foto = "sample.jpg";
        String beschreibung = "Beschreibung der Anfrage";

        // Verwende den vollständigen Konstruktor der Anfrage-Klasse
        Anfrage mockAnfrage = new Anfrage(new Date(), foto, beschreibung, false, patientId, arztId);

        Mockito.when(anfrageService.createAnfrage(patientId, arztId, foto, beschreibung)).thenReturn(mockAnfrage);

        // Act & Assert
        mockMvc.perform(post("/api/anfragen/create")
                        .param("patientId", patientId.toString())
                        .param("arztId", arztId.toString())
                        .param("foto", foto)
                        .param("beschreibung", beschreibung)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.beschreibung").value(beschreibung));
    }

    @Test
    void getAnfragenForArzt_shouldReturnListOfAnfragen() throws Exception {
        // Arrange
        Long arztId = 2L;

        // Verwende den vollständigen Konstruktor der Anfrage-Klasse
        Anfrage anfrage1 = new Anfrage(new Date(), "foto1.jpg", "Anfrage 1", false, 1L, arztId);
        Anfrage anfrage2 = new Anfrage(new Date(), "foto2.jpg", "Anfrage 2", false, 3L, arztId);

        List<Anfrage> mockAnfragen = Arrays.asList(anfrage1, anfrage2);

        Mockito.when(anfrageService.getAnfragenForArzt(arztId)).thenReturn(mockAnfragen);

        // Act & Assert
        mockMvc.perform(get("/api/anfragen/arzt/" + arztId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].beschreibung").value("Anfrage 1"))
                .andExpect(jsonPath("$[1].beschreibung").value("Anfrage 2"));
    }

    @Test
    void getAnfragenForPatient_shouldReturnListOfAnfragen() throws Exception {
        // Arrange
        Long patientId = 1L;

        // Verwende den vollständigen Konstruktor der Anfrage-Klasse
        Anfrage anfrage1 = new Anfrage(new Date(), "foto1.jpg", "Anfrage 1", false, patientId, 2L);
        Anfrage anfrage2 = new Anfrage(new Date(), "foto2.jpg", "Anfrage 2", false, patientId, 3L);

        List<Anfrage> mockAnfragen = Arrays.asList(anfrage1, anfrage2);

        Mockito.when(anfrageService.getAnfragenForPatient(patientId)).thenReturn(mockAnfragen);

        // Act & Assert
        mockMvc.perform(get("/api/anfragen/patient/" + patientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].beschreibung").value("Anfrage 1"))
                .andExpect(jsonPath("$[1].beschreibung").value("Anfrage 2"));
    }

  /*  @Test
    void exportAnfrageToPdf_shouldReturnPdfFile() throws Exception {
        // Arrange
        Long anfrageId = 1L;
        byte[] pdfData = "PDF File Content".getBytes();

        Mockito.when(pdfExportAnfrageService.exportAnfrageToPdf(anfrageId)).thenReturn(pdfData);

        // Act & Assert
        mockMvc.perform(get("/api/anfragen/export/" + anfrageId)
                        .contentType(MediaType.APPLICATION_PDF))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/pdf"))
                .andExpect(header().string("Content-Disposition", "attachment; filename=anfrage_report.pdf"));
    }*/

    @Test
    void completeAnfrage_shouldReturnCompletedAnfrage() throws Exception {
        // Arrange
        Long anfrageId = 1L;

        // Verwende den vollständigen Konstruktor der Anfrage-Klasse
        Anfrage mockAnfrage = new Anfrage(new Date(), "foto.jpg", "Anfrage abgeschlossen", true, 1L, 2L);

        Mockito.when(anfrageService.completeAnfrage(anfrageId)).thenReturn(mockAnfrage);

        // Act & Assert
        mockMvc.perform(put("/api/anfragen/complete/" + anfrageId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.beschreibung").value("Anfrage abgeschlossen"))
                .andExpect(jsonPath("$.status").value(true));
    }
}