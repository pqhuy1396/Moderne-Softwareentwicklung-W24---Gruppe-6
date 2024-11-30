package com.gruppe6.econsult.ArztverwaltungTest.infrastructure.aspects;

import com.gruppe6.econsult.Arztverwaltung.application.service.ArztService;
import com.gruppe6.econsult.Arztverwaltung.domain.events.ArztController;
import com.gruppe6.econsult.Arztverwaltung.domain.model.Arzt;
import com.gruppe6.econsult.Arztverwaltung.infrastructure.aspects.LoggingAspectArzt;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class LoggingAspectArztTest {

    @Autowired
    private ArztController arztController;

    @MockBean
    private ArztService arztService;

    @Test
    void testLogBeforeAndAfterReturning() {
        // Arrange
        Long arztId = 1L;
        Arzt mockArzt = new Arzt(arztId, "Dr. Smith", true, "Cardiology", "123456", "dr.smith@example.com", "drsmith", "password");
        when(arztService.getArztById(arztId)).thenReturn(Optional.of(mockArzt));

        // Act
        ResponseEntity<Arzt> response = arztController.getArztById(arztId);

        // Assert
        verify(arztService).getArztById(arztId);
        assertEquals(mockArzt, response.getBody());
    }

    @Test
    void testLogAfterThrowing() {
        // Arrange
        Long arztId = 1L;
        when(arztService.getArztById(arztId)).thenThrow(new RuntimeException("Test Exception"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> arztController.getArztById(arztId));
        verify(arztService).getArztById(arztId);
    }
}
