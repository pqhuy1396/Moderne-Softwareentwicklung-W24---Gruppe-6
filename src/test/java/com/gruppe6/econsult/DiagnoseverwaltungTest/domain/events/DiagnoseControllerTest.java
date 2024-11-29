package com.gruppe6.econsult.DiagnoseverwaltungTest.domain.events;

import com.gruppe6.econsult.Diagnoseverwaltung.application.service.DiagnoseService;
import com.gruppe6.econsult.Diagnoseverwaltung.domain.events.DiagnoseController;
import com.gruppe6.econsult.Diagnoseverwaltung.domain.model.Diagnose;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(DiagnoseController.class)
class DiagnoseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiagnoseService diagnoseService;

    @Test
    void createDiagnose_shouldReturnCreatedDiagnose() throws Exception {
        // Arrange
        Long anfrageId = 1L;
        String diagnoseText = "Grippe diagnostiziert";
        Diagnose mockDiagnose = new Diagnose(null, diagnoseText, new Date(), 101L);

        Mockito.when(diagnoseService.createDiagnose(anfrageId, diagnoseText)).thenReturn(mockDiagnose);

        // Act & Assert
        mockMvc.perform(post("/api/diagnosen/create")
                        .param("anfrageId", anfrageId.toString())
                        .param("diagnoseText", diagnoseText)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.diagnoseText").value(diagnoseText));
    }

    @Test
    void createDiagnose_shouldReturnBadRequestWhenInvalid() throws Exception {
        // Arrange
        Long anfrageId = 1L;
        String diagnoseText = "Grippe diagnostiziert";

        Mockito.when(diagnoseService.createDiagnose(anfrageId, diagnoseText))
                .thenThrow(new IllegalArgumentException("Invalid Anfrage ID"));

        // Act & Assert
        mockMvc.perform(post("/api/diagnosen/create")
                        .param("anfrageId", anfrageId.toString())
                        .param("diagnoseText", diagnoseText)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getDiagnoseByAnfrageId_shouldReturnDiagnoseWhenFound() throws Exception {
        // Arrange
        Long anfrageId = 1L;
        Diagnose mockDiagnose = new Diagnose(null, "Grippe diagnostiziert", new Date(), 101L);

        Mockito.when(diagnoseService.getDiagnoseByAnfrageId(anfrageId)).thenReturn(mockDiagnose);

        // Act & Assert
        mockMvc.perform(get("/api/diagnosen/anfrage/" + anfrageId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.diagnoseText").value("Grippe diagnostiziert"));
    }

    @Test
    void getDiagnoseByAnfrageId_shouldReturnNotFoundWhenNotFound() throws Exception {
        // Arrange
        Long anfrageId = 1L;

        Mockito.when(diagnoseService.getDiagnoseByAnfrageId(anfrageId)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/api/diagnosen/anfrage/" + anfrageId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getDiagnosenForArzt_shouldReturnListOfDiagnosen() throws Exception {
        // Arrange
        Long arztId = 101L;
        Diagnose diagnose1 = new Diagnose(null, "Grippe", new Date(), arztId);
        Diagnose diagnose2 = new Diagnose(null, "Fieber", new Date(), arztId);

        List<Diagnose> mockDiagnosen = Arrays.asList(diagnose1, diagnose2);

        Mockito.when(diagnoseService.getDiagnosenForArzt(arztId)).thenReturn(mockDiagnosen);

        // Act & Assert
        mockMvc.perform(get("/api/diagnosen/arzt/" + arztId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].diagnoseText").value("Grippe"))
                .andExpect(jsonPath("$[1].diagnoseText").value("Fieber"));
    }
}
