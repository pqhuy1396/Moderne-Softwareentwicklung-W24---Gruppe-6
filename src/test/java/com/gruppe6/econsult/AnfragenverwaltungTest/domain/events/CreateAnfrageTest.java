package com.gruppe6.econsult.AnfragenverwaltungTest.domain.events;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.gruppe6.econsult.Anfragenverwaltung.application.service.AnfrageService;
import com.gruppe6.econsult.Anfragenverwaltung.domain.events.CreateAnfrage;
import com.gruppe6.econsult.Anfragenverwaltung.domain.model.Anfrage;

import java.util.Date;

@WebMvcTest(CreateAnfrage.class)
public class CreateAnfrageTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnfrageService anfrageService;

    @Test
    void createAnfrage_shouldReturnCreatedAnfrage() throws Exception {
        Long patientId = 1L;
        Long arztId = 2L;
        String foto = "sample.jpg";
        String beschreibung = "Beschreibung der Anfrage";

        Anfrage mockAnfrage = new Anfrage(new Date(), foto, beschreibung, false, patientId, arztId);

        Mockito.when(anfrageService.createAnfrage(patientId, arztId, foto, beschreibung)).thenReturn(mockAnfrage);

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
    void createAnfrage_shouldReturnBadRequestForMissingParameters() throws Exception {
        mockMvc.perform(post("/api/anfragen/create")
                        .param("patientId", "")
                        .param("arztId", "2")
                        .param("foto", "sample.jpg")
                        .param("beschreibung", "Beschreibung der Anfrage")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createAnfrage_shouldHandleIllegalArgumentException() throws Exception {
        Long patientId = 1L;
        Long arztId = 2L;
        String foto = "sample.jpg";
        String beschreibung = "Beschreibung der Anfrage";

        Mockito.when(anfrageService.createAnfrage(patientId, arztId, foto, beschreibung))
                .thenThrow(new IllegalArgumentException("Invalid data"));

        mockMvc.perform(post("/api/anfragen/create")
                        .param("patientId", patientId.toString())
                        .param("arztId", arztId.toString())
                        .param("foto", foto)
                        .param("beschreibung", beschreibung)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}