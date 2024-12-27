package com.gruppe6.econsult.ArztverwaltungTest.domain.events;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.gruppe6.econsult.Arztverwaltung.application.service.ArztService;
import com.gruppe6.econsult.Arztverwaltung.domain.events.ArztRegister;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
@WebMvcTest(ArztRegister.class)
public class ArztRegisterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArztService arztService;

    @Test
    void testRegisterSuccess() throws Exception {
        when(arztService.generateRandomId()).thenReturn(1L);

        mockMvc.perform(post("/api/arzt/register")
                        .param("name", "Dr. Smith")
                        .param("email", "dr.smith@example.com")
                        .param("username", "drsmith")
                        .param("password", "password")
                        .param("fachrichtung", "Cardiology")
                        .param("lizenznummer", "123456")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isCreated())
                .andExpect(content().string("Arzt registered successfully"));
    }

    @Test
    void testRegisterMissingFields() throws Exception {
        mockMvc.perform(post("/api/arzt/register")
                        .param("name", "Dr. Smith")
                        .param("email", "dr.smith@example.com")
                        .param("username", "drsmith")
                        .param("password", "password")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }
}
