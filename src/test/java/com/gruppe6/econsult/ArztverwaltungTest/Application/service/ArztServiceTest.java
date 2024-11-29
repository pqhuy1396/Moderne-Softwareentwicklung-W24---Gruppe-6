package com.gruppe6.econsult.ArztverwaltungTest.Application.service;

import com.gruppe6.econsult.Arztverwaltung.application.service.ArztService;
import com.gruppe6.econsult.Arztverwaltung.domain.model.Arzt;
import com.gruppe6.econsult.Arztverwaltung.infrastructure.repository.ArztRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArztServiceTest {

    @InjectMocks
    private ArztService arztService;

    @Mock
    private ArztRepository arztRepository;

    @Test
    void getArztById_shouldReturnArztWhenFound() {
        Long arztId = 1L;
        Arzt arzt = new Arzt();
        arzt.setId(arztId);

        when(arztRepository.findArztById(arztId)).thenReturn(Optional.of(arzt));

        Optional<Arzt> result = arztService.getArztById(arztId);

        assertTrue(result.isPresent(), "Arzt sollte vorhanden sein");
        assertEquals(arztId, result.get().getId(), "Arzt-ID sollte übereinstimmen");
        verify(arztRepository, times(1)).findArztById(arztId);
    }

    @Test
    void getArztById_shouldReturnEmptyWhenNotFound() {
        Long arztId = 1L;

        when(arztRepository.findArztById(arztId)).thenReturn(Optional.empty());

        Optional<Arzt> result = arztService.getArztById(arztId);

        assertFalse(result.isPresent(), "Arzt sollte nicht gefunden werden");
        verify(arztRepository, times(1)).findArztById(arztId);
    }

    @Test
    void getAllArzt_shouldReturnAllDoctors() {
        List<Arzt> arztList = Arrays.asList(
                new Arzt(1L, "Dr. Max Mustermann", true, "Dermatology", "12345", "Mustermann@example.com", "Mustermann_Max", "password"),
                new Arzt(2L, "Dr. Jane Doe", true, "Cardiology", "67890", "jane.doe@example.com", "jane_doe", "password123")
        );

        when(arztRepository.findAll()).thenReturn(arztList);

        List<Arzt> result = arztService.getAllArzt();

        assertEquals(2, result.size(), "Es sollten zwei Ärzte zurückgegeben werden");
        assertEquals("Dr. Max Mustermann", result.get(0).getName(), "Name des ersten Arztes sollte übereinstimmen");
        assertEquals("Dr. Jane Doe", result.get(1).getName(), "Name des zweiten Arztes sollte übereinstimmen");
        verify(arztRepository, times(1)).findAll();
    }

    @Test
    void saveArzt_shouldSaveAndReturnArzt() {
        Arzt arzt = new Arzt();
        arzt.setName("Dr. Max Mustermann");

        when(arztRepository.save(arzt)).thenReturn(arzt);

        Arzt result = arztService.saveArzt(arzt);

        assertNotNull(result, "Das gespeicherte Arztobjekt sollte nicht null sein");
        assertEquals("Dr. Max Mustermann", result.getName(), "Der Name des Arztes sollte übereinstimmen");
        verify(arztRepository, times(1)).save(arzt);
    }

    @Test
    void deleteArztById_shouldInvokeRepositoryDelete() {
        Long arztId = 1L;

        arztService.deleteArztById(arztId);

        verify(arztRepository, times(1)).deleteArztById(arztId);
    }

    @Test
    void getArztByUsername_shouldReturnArztWhenFound() {
        String username = "arzt123";
        Arzt arzt = new Arzt();
        arzt.setUsername(username);

        when(arztRepository.findByUsername(username)).thenReturn(Optional.of(arzt));

        Optional<Arzt> result = arztService.getArztByUsername(username);

        assertTrue(result.isPresent(), "Arzt sollte vorhanden sein");
        assertEquals(username, result.get().getUsername(), "Der Benutzername sollte übereinstimmen");
        verify(arztRepository, times(1)).findByUsername(username);
    }

    @Test
    void getArztByUsername_shouldReturnEmptyWhenNotFound() {
        String username = "arzt123";

        when(arztRepository.findByUsername(username)).thenReturn(Optional.empty());

        Optional<Arzt> result = arztService.getArztByUsername(username);

        assertFalse(result.isPresent(), "Arzt sollte nicht gefunden werden");
        verify(arztRepository, times(1)).findByUsername(username);
    }

    @Test
    void getArztByEmail_shouldReturnArztWhenFound() {
        String email = "arzt@example.com";
        Arzt arzt = new Arzt();
        arzt.setEmail(email);

        when(arztRepository.findByEmail(email)).thenReturn(Optional.of(arzt));

        Optional<Arzt> result = arztService.getArztByEmail(email);

        assertTrue(result.isPresent(), "Arzt sollte vorhanden sein");
        assertEquals(email, result.get().getEmail(), "Die E-Mail sollte übereinstimmen");
        verify(arztRepository, times(1)).findByEmail(email);
    }

    @Test
    void getArztByEmail_shouldReturnEmptyWhenNotFound() {
        String email = "arzt@example.com";

        when(arztRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<Arzt> result = arztService.getArztByEmail(email);

        assertFalse(result.isPresent(), "Arzt sollte nicht gefunden werden");
        verify(arztRepository, times(1)).findByEmail(email);
    }

    @Test
    void generateRandomId_shouldReturnValidId() {
        Long generatedId = arztService.generateRandomId();

        assertNotNull(generatedId, "Die generierte ID sollte nicht null sein");
        assertTrue(generatedId >= 1000000000L && generatedId < 10000000000L, "Die generierte ID sollte im erwarteten Bereich liegen");
    }
}
