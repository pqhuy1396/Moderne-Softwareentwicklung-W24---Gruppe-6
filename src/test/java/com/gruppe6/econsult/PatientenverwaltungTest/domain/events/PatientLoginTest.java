package com.gruppe6.econsult.PatientenverwaltungTest.domain.events;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gruppe6.econsult.Patientenverwaltung.application.service.PatientenService;
import com.gruppe6.econsult.Patientenverwaltung.domain.events.PatientLogin;
import com.gruppe6.econsult.Patientenverwaltung.domain.model.Patient;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PatientLogin.class)
class PatientLoginTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientenService patientenService;

    @Test
    void login_shouldReturnOkWhenCredentialsAreValid() throws Exception {
        String username = "max_muster";
        String password = "password";
        Patient mockPatient = new Patient(1L, "Max Mustermann", false, "123 Main St", "1990-01-01", "mustermann@example.com", username, password);

        when(patientenService.getPatientByUsername(username)).thenReturn(Optional.of(mockPatient));

        mockMvc.perform(post("/api/patients/login")
                        .param("username", username)
                        .param("password", password)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());
    }

    @Test
    void login_shouldReturnUnauthorizedWhenCredentialsAreInvalid() throws Exception {
        String username = "max_muster";
        String password = "wrongpassword";
        Patient mockPatient = new Patient(1L, "Max Mustermann", false, "123 Main St", "1990-01-01", "mustermann@example.com", username, "correctpassword");

        when(patientenService.getPatientByUsername(username)).thenReturn(Optional.of(mockPatient));

        mockMvc.perform(post("/api/patients/login")
                        .param("username", username)
                        .param("password", password)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isUnauthorized());
    }
}