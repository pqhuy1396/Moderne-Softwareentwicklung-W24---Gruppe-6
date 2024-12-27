package com.gruppe6.econsult.DiagnoseverwaltungTest.domain.events;

import com.gruppe6.econsult.Diagnoseverwaltung.application.service.DiagnoseService;
import com.gruppe6.econsult.Diagnoseverwaltung.domain.events.GetDiagnoseByAnfrageId;
import com.gruppe6.econsult.Diagnoseverwaltung.domain.model.Diagnose;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GetDiagnoseByAnfrageId.class)
public class GetDiagnoseByAnfrageIdTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiagnoseService diagnoseService;

    @Test
    void getDiagnoseByAnfrageId_shouldReturnDiagnoseWhenFound() throws Exception {
        Long anfrageId = 1L;
        Diagnose mockDiagnose = new Diagnose(null, "Grippe diagnostiziert", new Date(), 101L);

        Mockito.when(diagnoseService.getDiagnoseByAnfrageId(anfrageId)).thenReturn(mockDiagnose);

        mockMvc.perform(get("/api/diagnosen/anfrage/{anfrageId}", anfrageId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.diagnoseText").value("Grippe diagnostiziert"));
    }

    @Test
    void getDiagnoseByAnfrageId_shouldReturnNotFoundWhenNotFound() throws Exception {
        Long anfrageId = 1L;

        Mockito.when(diagnoseService.getDiagnoseByAnfrageId(anfrageId)).thenReturn(null);

        mockMvc.perform(get("/api/diagnosen/anfrage/{anfrageId}", anfrageId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}