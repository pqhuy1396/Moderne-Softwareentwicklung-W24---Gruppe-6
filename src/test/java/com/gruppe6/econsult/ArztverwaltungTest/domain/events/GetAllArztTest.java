package com.gruppe6.econsult.ArztverwaltungTest.domain.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gruppe6.econsult.Arztverwaltung.application.service.ArztService;
import com.gruppe6.econsult.Arztverwaltung.domain.events.GetAllArzt;
import com.gruppe6.econsult.Arztverwaltung.domain.model.Arzt;

@SpringBootTest
public class GetAllArztTest {

    @Autowired
    private GetAllArzt getAllArztController;

    @MockBean
    private ArztService arztService;

    @Test
    void getAllArzt_shouldReturnAllArzt() {
        List<Arzt> arztList = Arrays.asList(
                new Arzt(1L, "Dr. Max Mustermann", true, "Dermatology", "12345", "Mustermann@example.com", "Mustermann_Max", "password"),
                new Arzt(2L, "Dr. Jane Smith", true, "Cardiology", "67890", "jane.smith@example.com", "jane_smith", "password")
        );

        when(arztService.getAllArzt()).thenReturn(arztList);

        ResponseEntity<List<Arzt>> response = getAllArztController.getAllArzt();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }
}
