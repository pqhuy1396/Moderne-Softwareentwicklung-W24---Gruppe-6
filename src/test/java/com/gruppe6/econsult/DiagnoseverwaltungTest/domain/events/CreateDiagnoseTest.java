package com.gruppe6.econsult.DiagnoseverwaltungTest.domain.events;

import com.gruppe6.econsult.Diagnoseverwaltung.application.service.DiagnoseService;
import com.gruppe6.econsult.Diagnoseverwaltung.domain.events.CreateDiagnose;
import com.gruppe6.econsult.Diagnoseverwaltung.domain.model.Diagnose;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CreateDiagnose.class)
public class CreateDiagnoseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiagnoseService diagnoseService;

    @Test
    void createDiagnose_shouldReturnCreatedDiagnose() throws Exception {
        Long anfrageId = 1L;
        String diagnoseText = "Grippe diagnostiziert";
        Diagnose mockDiagnose = new Diagnose(null, diagnoseText, new Date(), 101L);

        Mockito.when(diagnoseService.createDiagnose(anfrageId, diagnoseText)).thenReturn(mockDiagnose);

        mockMvc.perform(post("/api/diagnosen/create")
                        .param("anfrageId", anfrageId.toString())
                        .param("diagnoseText", diagnoseText)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.diagnoseText").value(diagnoseText));
    }

    @Test
    void createDiagnose_shouldReturnBadRequestWhenInvalid() throws Exception {
        Long anfrageId = 1L;
        String diagnoseText = "Grippe diagnostiziert";

        Mockito.when(diagnoseService.createDiagnose(anfrageId, diagnoseText))
                .thenThrow(new IllegalArgumentException("Invalid Anfrage ID"));

        mockMvc.perform(post("/api/diagnosen/create")
                        .param("anfrageId", anfrageId.toString())
                        .param("diagnoseText", diagnoseText)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}