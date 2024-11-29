package com.gruppe6.econsult.ArztverwaltungTest.domain.events;

import com.gruppe6.econsult.Arztverwaltung.application.service.ArztService;
import com.gruppe6.econsult.Arztverwaltung.domain.events.ArztController;
import com.gruppe6.econsult.Arztverwaltung.domain.model.Arzt;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArztControllerTest {

    @Mock
    private ArztService arztService;

    @InjectMocks
    private ArztController arztController;

    @Test
    void getAllArzt_shouldReturnAllArzt() {
        List<Arzt> arztList = Arrays.asList(
                new Arzt(1L, "Dr. Max Mustermann", true, "Dermatology", "12345", "Mustermann@example.com", "Mustermann_Max", "password"),
                new Arzt(2L, "Dr. Jane Smith", true, "Cardiology", "67890", "jane.smith@example.com", "jane_smith", "password")
        );

        when(arztService.getAllArzt()).thenReturn(arztList);

        ResponseEntity<List<Arzt>> response = arztController.getAllArzt();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    void getArztById_shouldReturnArztWhenFound() {
        Arzt arzt = new Arzt(1L, "Dr. Max Mustermann", true, "Dermatology", "12345", "Mustermann@example.com", "Mustermann_Max", "password");

        when(arztService.getArztById(1L)).thenReturn(Optional.of(arzt));

        ResponseEntity<Arzt> response = arztController.getArztById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(arzt.getName(), Objects.requireNonNull(response.getBody()).getName());
        assertEquals(arzt.getEmail(), response.getBody().getEmail());
    }

    @Test
    void getArztById_shouldReturnNotFoundWhenArztNotExist() {
        when(arztService.getArztById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Arzt> response = arztController.getArztById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getArztByUsername_shouldReturnArztWhenFound() {
        Arzt arzt = new Arzt(1L, "Dr. Max Mustermann", true, "Dermatology", "12345", "Mustermann@example.com", "Mustermann_Max", "password");

        when(arztService.getArztByUsername("Mustermann_Max")).thenReturn(Optional.of(arzt));

        ResponseEntity<Arzt> response = arztController.getArztByUsername("Mustermann_Max");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(arzt.getUsername(), Objects.requireNonNull(response.getBody()).getUsername());
    }

    @Test
    void getArztByUsername_shouldReturnNotFoundWhenArztNotExist() {
        when(arztService.getArztByUsername(anyString())).thenReturn(Optional.empty());

        ResponseEntity<Arzt> response = arztController.getArztByUsername("Mustermann_Max");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getArztByEmail_shouldReturnArztWhenFound() {
        Arzt arzt = new Arzt(1L, "Dr. Max Mustermann", true, "Dermatology", "12345", "Mustermann@example.com", "Mustermann_Max", "password");

        when(arztService.getArztByEmail("Mustermann@example.com")).thenReturn(Optional.of(arzt));

        ResponseEntity<Arzt> response = arztController.getArztByEmail("Mustermann@example.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(arzt.getEmail(), Objects.requireNonNull(response.getBody()).getEmail());
    }

    @Test
    void getArztByEmail_shouldReturnNotFoundWhenArztNotExist() {
        when(arztService.getArztByEmail(anyString())).thenReturn(Optional.empty());

        ResponseEntity<Arzt> response = arztController.getArztByEmail("Mustermann@example.com");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void saveArzt_shouldSaveArzt() {
        Arzt arzt = new Arzt(1L, "Dr. Max Mustermann", true, "Dermatology", "12345", "Mustermann@example.com", "Mustermann_Max", "password");

        when(arztService.saveArzt(any(Arzt.class))).thenReturn(arzt);

        ResponseEntity<Arzt> response = arztController.saveArzt(arzt);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(arzt.getName(), Objects.requireNonNull(response.getBody()).getName());
    }

    @Test
    void deleteArztById_shouldDeleteArztWhenFound() {
        Arzt arzt = new Arzt(1L, "Dr. Max Mustermann", true, "Dermatology", "12345", "Mustermann@example.com", "Mustermann_Max", "password");

        when(arztService.getArztById(1L)).thenReturn(Optional.of(arzt));
        doNothing().when(arztService).deleteArztById(1L);

        ResponseEntity<Void> response = arztController.deleteArztById(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteArztById_shouldReturnNotFoundWhenArztNotExist() {
        when(arztService.getArztById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Void> response = arztController.deleteArztById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
