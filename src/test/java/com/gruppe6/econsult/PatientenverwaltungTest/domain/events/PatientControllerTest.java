package com.gruppe6.econsult.PatientenverwaltungTest.domain.events;

import com.gruppe6.econsult.Patientenverwaltung.application.service.PatientenService;
import com.gruppe6.econsult.Patientenverwaltung.domain.events.PatientController;
import com.gruppe6.econsult.Patientenverwaltung.domain.model.Patient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientenService patientenService;

    @Test
    void getPatientById_shouldReturnPatientWhenFound() throws Exception {
        // Arrange
        Long patientId = 1L;
        Patient mockPatient = new Patient(patientId, "Max Mustermann", false, "123 Main St", "1990-01-01", "mustermann@example.com", "max_muster", "password");

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

    @Test
    void login_shouldReturnOkWhenCredentialsAreValid() throws Exception {
        // Arrange
        String username = "max_muster";
        String password = "password";
        Patient mockPatient = new Patient(1L, "Max Mustermann", false, "123 Main St", "1990-01-01", "mustermann@example.com", username, password);

        when(patientenService.getPatientByUsername(username)).thenReturn(Optional.of(mockPatient));

        // Act & Assert
        mockMvc.perform(post("/api/patients/login")
                        .param("username", username)
                        .param("password", password)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().string("Logged in as: normal"));
    }

    @Test
    void login_shouldReturnUnauthorizedWhenCredentialsAreInvalid() throws Exception {
        // Arrange
        String username = "max_muster";
        String password = "wrongpassword";
        Patient mockPatient = new Patient(1L, "Max Mustermann", false, "123 Main St", "1990-01-01", "mustermann@example.com", username, "correctpassword");

        when(patientenService.getPatientByUsername(username)).thenReturn(Optional.of(mockPatient));

        // Act & Assert
        mockMvc.perform(post("/api/patients/login")
                        .param("username", username)
                        .param("password", password)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid username or password"));
    }

    @Test
    void register_shouldCreateNewPatient() throws Exception {
        // Arrange
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

        // Act & Assert
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
        // Act & Assert
        mockMvc.perform(post("/api/patients/register")
                        .param("name", "Max Mustermann")
                        .param("email", "mustermann@example.com")
                        .param("username", "max_muster")
                        .param("password", "password")
                        // address and birthDate are missing
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Missing address or birthDate for Patient"));
    }
}
