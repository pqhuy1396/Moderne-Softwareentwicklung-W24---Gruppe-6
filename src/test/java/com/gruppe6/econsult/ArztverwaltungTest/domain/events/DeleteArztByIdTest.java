package com.gruppe6.econsult.ArztverwaltungTest.domain.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
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
import com.gruppe6.econsult.Arztverwaltung.domain.events.DeleteArztById;
import com.gruppe6.econsult.Arztverwaltung.domain.model.Arzt;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
@WebMvcTest(DeleteArztById.class)
public class DeleteArztByIdTest {

    @Autowired
    private DeleteArztById deleteArztByIdController;

    @MockBean
    private ArztService arztService;

    @Test
    void deleteArztById_shouldReturnNoContentWhenArztExists() {
        // Arrange
        Arzt arzt = new Arzt(1L, "Dr. Max Mustermann", true, "Dermatology", "12345", "Mustermann@example.com", "Mustermann_Max", "password");
        when(arztService.getArztById(1L)).thenReturn(Optional.of(arzt));
        doNothing().when(arztService).deleteArztById(1L);

        // Act
        ResponseEntity<Void> response = deleteArztByIdController.deleteArztById(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteArztById_shouldReturnNotFoundWhenArztDoesNotExist() {
        // Arrange
        when(arztService.getArztById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Void> response = deleteArztByIdController.deleteArztById(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
