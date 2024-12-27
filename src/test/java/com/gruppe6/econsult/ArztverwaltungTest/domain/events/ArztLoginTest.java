package com.gruppe6.econsult.ArztverwaltungTest.domain.events;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.gruppe6.econsult.Arztverwaltung.application.service.ArztService;
import com.gruppe6.econsult.Arztverwaltung.domain.events.ArztLogin;
import com.gruppe6.econsult.Arztverwaltung.domain.model.Arzt;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
@WebMvcTest(ArztLogin.class)
public class ArztLoginTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArztService arztService;

    @Test
    void testLoginSuccess() throws Exception {
        Arzt arzt = new Arzt(1L, "Dr. Smith", true, "Cardiology", "123456", "dr.smith@example.com", "drsmith", "password");
        when(arztService.getArztByUsername("drsmith")).thenReturn(Optional.of(arzt));

        mockMvc.perform(post("/api/arzt/login")
                        .param("username", "drsmith")
                        .param("password", "password")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());
    }

    @Test
    void testLoginFailure() throws Exception {
        when(arztService.getArztByUsername("wronguser")).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/arzt/login")
                        .param("username", "wronguser")
                        .param("password", "wrongpass")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isUnauthorized());
    }
}
