package com.gruppe6.econsult.ArztverwaltungTest.domain.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gruppe6.econsult.Arztverwaltung.application.service.ArztService;
import com.gruppe6.econsult.Arztverwaltung.domain.events.GetArztById;
import com.gruppe6.econsult.Arztverwaltung.domain.model.Arzt;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
@WebMvcTest(GetArztById.class)
public class GetArztByIdTest {

    @Autowired
    private GetArztById getArztByIdController;

    @MockBean
    private ArztService arztService;

    @Test
    void getArztById_shouldReturnArztWhenFound() {
        // Arrange
        Arzt arzt = new Arzt(1L, "Dr. Max Mustermann", true, "Dermatology", "12345", "Mustermann@example.com", "Mustermann_Max", "password");
        when(arztService.getArztById(1L)).thenReturn(Optional.of(arzt));

        // Act
        ResponseEntity<Arzt> response = getArztByIdController.getArztById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(arzt.getName(), response.getBody().getName());
        assertEquals(arzt.getEmail(), response.getBody().getEmail());
    }

    @Test
    void getArztById_shouldReturnNotFoundWhenArztDoesNotExist() {
        // Arrange
        when(arztService.getArztById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Arzt> response = getArztByIdController.getArztById(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}