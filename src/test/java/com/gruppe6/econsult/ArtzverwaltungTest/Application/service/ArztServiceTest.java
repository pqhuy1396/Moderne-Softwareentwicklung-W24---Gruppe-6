package com.gruppe6.econsult.ArtzverwaltungTest.Service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


import com.gruppe6.econsult.Arztverwaltung.application.service.ArztService;
import com.gruppe6.econsult.Arztverwaltung.domain.model.Arzt;
import com.gruppe6.econsult.Arztverwaltung.infrastructure.repository.ArztRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;



class ArztServiceTest {

    @InjectMocks
    private ArztService arztService;

    @Mock
    private ArztRepository arztRepository;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetArztById() {
        Long arztId = 1L;
        Arzt arzt = new Arzt();
        arzt.setId(arztId);
        when(arztRepository.findArztById(arztId)).thenReturn(Optional.of(arzt));

        Optional<Arzt> result = arztService.getArztById(arztId);

        assertTrue(result.isPresent());
        assertEquals(arztId, result.get().getId());
        verify(arztRepository, times(1)).findArztById(arztId);
    }

    @Test
    void testSaveArzt() {
        Arzt arzt = new Arzt();
        when(arztRepository.save(arzt)).thenReturn(arzt);

        Arzt result = arztService.saveArzt(arzt);

        assertNotNull(result);
        assertEquals(arzt, result);
        verify(arztRepository, times(1)).save(arzt);
    }

    @Test
    void testDeleteArztById() {
        Long arztId = 1L;

        arztService.deleteArztById(arztId);

        verify(arztRepository, times(1)).deleteArztById(arztId);
    }

    @Test
    void testGetArztByUsername() {
        String username = "arzt123";
        Arzt arzt = new Arzt();
        arzt.setUsername(username);
        when(arztRepository.findByUsername(username)).thenReturn(Optional.of(arzt));

        Optional<Arzt> result = arztService.getArztByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
        verify(arztRepository, times(1)).findByUsername(username);
    }
}
