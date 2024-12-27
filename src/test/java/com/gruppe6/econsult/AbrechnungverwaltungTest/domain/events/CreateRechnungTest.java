package com.gruppe6.econsult.AbrechnungverwaltungTest.domain.events;

import com.gruppe6.econsult.Abrechnungverwaltung.domain.model.Rechnung;
import com.gruppe6.econsult.Abrechnungverwaltung.infrastructure.reponsitory.RechnungRepository;
import com.gruppe6.econsult.Abrechnungverwaltung.domain.events.CreateRechnung;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CreateRechnung.class)
public class CreateRechnungTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RechnungRepository rechnungRepository;

    @Test
    void createRechnung_shouldReturnCreatedRechnung() throws Exception {
        Long patientId = 1L;
        String rechnungNummer = UUID.randomUUID().toString();
        Rechnung mockRechnung = new Rechnung(rechnungNummer, patientId);
        mockRechnung.setDatum(new Date());
        mockRechnung.setBetrag(20);

        Mockito.when(rechnungRepository.save(Mockito.any(Rechnung.class))).thenReturn(mockRechnung);

        mockMvc.perform(post("/api/rechnungen/create")
                        .param("idPatient", patientId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rechnungNummer").value(rechnungNummer))
                .andExpect(jsonPath("$.betrag").value(20));
    }
}