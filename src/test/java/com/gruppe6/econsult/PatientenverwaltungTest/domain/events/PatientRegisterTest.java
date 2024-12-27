package com.gruppe6.econsult.PatientenverwaltungTest.domain.events;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gruppe6.econsult.Patientenverwaltung.application.service.PatientenService;
import com.gruppe6.econsult.Patientenverwaltung.domain.events.PatientRegister;
import com.gruppe6.econsult.Patientenverwaltung.domain.model.Patient;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PatientRegister.class)
class PatientRegisterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientenService patientenService;

    @Test
    void register_shouldCreateNewPatient() throws Exception {
        String name = "Max Mustermann";
        String email = "mustermann@example.com";
        String username = "max_muster";
        String password = "password";
        String address = "123 Main St";
        String birthDate = "1990-01-01";
        Long id = 1L;

        when(patientenService.generateRandomId()).thenReturn(id);
        Patient newPatient = new Patient(id, name, false, address, birthDate, email, username, password);

        when(patientenService.savePatient(any(Patient.class))).thenReturn(newPatient);

        mockMvc.perform(post("/api/patients/register")
                        .param("name", name)
                        .param("email", email)
                        .param("username", username)
                        .param("password", password)
                        .param("address", address)
                        .param("birthDate", birthDate)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isCreated())
                .andExpect(content().string("Patient registered successfully"));
    }

    @Test
    void register_shouldReturnBadRequestWhenParametersAreMissing() throws Exception {
        mockMvc.perform(post("/api/patients/register")
                        .param("name", "Max Mustermann")
                        .param("email", "mustermann@example.com")
                        .param("username", "max_muster")
                        .param("password", "password")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }
}