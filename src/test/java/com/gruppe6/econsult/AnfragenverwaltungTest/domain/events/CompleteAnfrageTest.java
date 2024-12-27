package com.gruppe6.econsult.AnfragenverwaltungTest.domain.events;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.gruppe6.econsult.Anfragenverwaltung.application.service.AnfrageService;
import com.gruppe6.econsult.Anfragenverwaltung.domain.events.CompleteAnfrage;
import com.gruppe6.econsult.Anfragenverwaltung.domain.model.Anfrage;

import java.util.Date;

@WebMvcTest(CompleteAnfrage.class)
public class CompleteAnfrageTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnfrageService anfrageService;

    @Test
    void completeAnfrage_shouldReturnCompletedAnfrage() throws Exception {
        Long anfrageId = 1L;

        Anfrage mockAnfrage = new Anfrage(new Date(), "foto.jpg", "Anfrage abgeschlossen", true, 1L, 2L);

        Mockito.when(anfrageService.completeAnfrage(anfrageId)).thenReturn(mockAnfrage);

        mockMvc.perform(put("/api/anfragen/complete/{anfrageId}", anfrageId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.beschreibung").value("Anfrage abgeschlossen"))
                .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    void completeAnfrage_shouldReturnNotFoundForInvalidId() throws Exception {
        Long anfrageId = 1L;

        Mockito.when(anfrageService.completeAnfrage(anfrageId)).thenThrow(new IllegalArgumentException("Anfrage not found"));

        mockMvc.perform(put("/api/anfragen/complete/{anfrageId}", anfrageId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void completeAnfrage_shouldReturnNotFoundForNullId() throws Exception {
        mockMvc.perform(put("/api/anfragen/complete/{anfrageId}", (Object) null)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
