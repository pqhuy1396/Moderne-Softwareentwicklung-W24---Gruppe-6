package com.gruppe6.econsult.DiagnoseverwaltungTest.domain.events;

import com.gruppe6.econsult.Diagnoseverwaltung.application.service.DiagnoseService;
import com.gruppe6.econsult.Diagnoseverwaltung.domain.events.GetDiagnosenForArzt;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GetDiagnosenForArzt.class)
public class GetDiagnosenForArztTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiagnoseService diagnoseService;

    @Test
    void getDiagnosenForArzt_shouldReturnListOfDiagnosen() throws Exception {
        Long arztId = 101L;
        Diagnose diagnose1 = new Diagnose(null, "Grippe", new Date(), arztId);
        Diagnose diagnose2 = new Diagnose(null, "Fieber", new Date(), arztId);

        List<Diagnose> mockDiagnosen = Arrays.asList(diagnose1, diagnose2);

        Mockito.when(diagnoseService.getDiagnosenForArzt(arztId)).thenReturn(mockDiagnosen);

        mockMvc.perform(get("/api/diagnosen/arzt/{arztId}", arztId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].diagnoseText").value("Grippe"))
                .andExpect(jsonPath("$[1].diagnoseText").value("Fieber"));
    }
}
