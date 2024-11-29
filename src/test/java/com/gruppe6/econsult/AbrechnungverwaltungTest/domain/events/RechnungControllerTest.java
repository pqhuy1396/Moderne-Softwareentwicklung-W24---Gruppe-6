package com.gruppe6.econsult.AbrechnungverwaltungTest.domain.events;

import com.gruppe6.econsult.Abrechnungverwaltung.domain.model.Rechnung;
import com.gruppe6.econsult.Abrechnungverwaltung.infrastructure.reponsitory.RechnungRepository;
import com.gruppe6.econsult.Abrechnungverwaltung.domain.events.RechnungController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(RechnungController.class)
class RechnungControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RechnungRepository rechnungRepository;

    @Test
    void createRechnung_shouldReturnCreatedRechnung() throws Exception {
        // Mock-Daten vorbereiten
        Long patientId = 1L;
        String rechnungNummer = UUID.randomUUID().toString();
        Rechnung mockRechnung = new Rechnung(rechnungNummer, patientId);
        mockRechnung.setDatum(new Date());
        mockRechnung.setBetrag(20);

        // Mock-Verhalten
        Mockito.when(rechnungRepository.save(Mockito.any(Rechnung.class))).thenReturn(mockRechnung);

        // Test-Aufruf
        mockMvc.perform(post("/api/rechnungen/create")
                        .param("idPatient", patientId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rechnungNummer").value(rechnungNummer))
                .andExpect(jsonPath("$.betrag").value(20));
    }

    @Test
    void getRechnungenByPatient_shouldReturnListOfRechnungen() throws Exception {
        // Mock-Daten
        Long patientId = 1L;
        Rechnung rechnung1 = new Rechnung("123-abc", patientId);
        rechnung1.setDatum(new Date());
        rechnung1.setBetrag(20);

        Rechnung rechnung2 = new Rechnung("456-def", patientId);
        rechnung2.setDatum(new Date());
        rechnung2.setBetrag(20);

        List<Rechnung> mockRechnungen = Arrays.asList(rechnung1, rechnung2);

        // Mock-Verhalten
        Mockito.when(rechnungRepository.findByIdPatient(patientId)).thenReturn(mockRechnungen);

        // Test-Aufruf
        mockMvc.perform(get("/api/rechnungen/patient/" + patientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].rechnungNummer").value("123-abc"))
                .andExpect(jsonPath("$[1].betrag").value(20));
    }

    @Test
    void getRechnungenByPatient_shouldReturnNoContentWhenEmpty() throws Exception {
        // Mock-Verhalten für leere Ergebnisse
        Long patientId = 2L;
        Mockito.when(rechnungRepository.findByIdPatient(patientId)).thenReturn(List.of());

        // Test-Aufruf
        mockMvc.perform(get("/api/rechnungen/patient/" + patientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
