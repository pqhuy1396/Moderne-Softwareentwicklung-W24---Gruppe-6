package com.gruppe6.econsult.PatientenverwaltungTest.domain.events;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gruppe6.econsult.Patientenverwaltung.application.service.PatientenService;
import com.gruppe6.econsult.Patientenverwaltung.domain.events.PatientGet;
import com.gruppe6.econsult.Patientenverwaltung.domain.model.Patient;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PatientGet.class)
class PatientGetTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientenService patientenService;

    @Test
    void getPatientById_shouldReturnPatientWhenFound() throws Exception {
        // Arrange
        Long patientId = 1L;
        Patient mockPatient = new Patient(patientId, "Max Mustermann", false, "mustermann@example.com", "1990-01-01", "123 Main St", "max_muster", "password");

        when(patientenService.getPatientById(patientId)).thenReturn(Optional.of(mockPatient));

        // Act & Assert
        mockMvc.perform(get("/api/patients/{id}", patientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Max Mustermann"))
                .andExpect(jsonPath("$.email").value("mustermann@example.com"));
    }

    @Test
    void getPatientById_shouldReturnNotFoundWhenPatientDoesNotExist() throws Exception {
        // Arrange
        Long patientId = 1L;

        when(patientenService.getPatientById(patientId)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/patients/{id}", patientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}