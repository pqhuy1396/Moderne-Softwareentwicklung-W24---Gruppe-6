package com.gruppe6.econsult.AnfragenverwaltungTest.domain.events;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.gruppe6.econsult.Anfragenverwaltung.application.service.AnfrageService;
import com.gruppe6.econsult.Anfragenverwaltung.domain.events.GetAnfragenForArzt;
import com.gruppe6.econsult.Anfragenverwaltung.domain.model.Anfrage;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@WebMvcTest(GetAnfragenForArzt.class)
public class GetAnfragenForArztTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnfrageService anfrageService;

    @Test
    void getAnfragenForArzt_shouldReturnListOfAnfragen() throws Exception {
        Long arztId = 2L;

        Anfrage anfrage1 = new Anfrage(new Date(), "foto1.jpg", "Anfrage 1", false, 1L, arztId);
        Anfrage anfrage2 = new Anfrage(new Date(), "foto2.jpg", "Anfrage 2", false, 3L, arztId);

        List<Anfrage> mockAnfragen = Arrays.asList(anfrage1, anfrage2);

        Mockito.when(anfrageService.getAnfragenForArzt(arztId)).thenReturn(mockAnfragen);

        mockMvc.perform(get("/api/anfragen/arzt/" + arztId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].beschreibung").value("Anfrage 1"))
                .andExpect(jsonPath("$[1].beschreibung").value("Anfrage 2"));
    }
}
