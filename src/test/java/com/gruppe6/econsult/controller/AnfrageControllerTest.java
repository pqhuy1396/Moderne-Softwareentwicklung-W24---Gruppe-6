package com.gruppe6.econsult.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gruppe6.econsult.model.Anfrage;
import com.gruppe6.econsult.service.AnfrageService;

class AnfrageControllerTest {

    @Mock
    private AnfrageService anfrageService;

    @InjectMocks
    private AnfrageController anfrageController;

    public AnfrageControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAnfrage() {
        Anfrage anfrage = new Anfrage(new Date(), "photo.jpg", "description", false, 1L, 2L);
        when(anfrageService.createAnfrage(1L, 2L, "photo.jpg", "description")).thenReturn(anfrage);

        ResponseEntity<Anfrage> response = anfrageController.createAnfrage(1L, 2L, "photo.jpg", "description");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(anfrage, response.getBody());
    }

    @Test
    void testGetAnfragenForArzt() {
        List<Anfrage> anfragen = Arrays.asList(new Anfrage(new Date(), "photo.jpg", "description", false, 1L, 2L));
        when(anfrageService.getAnfragenForArzt(2L)).thenReturn(anfragen);

        ResponseEntity<List<Anfrage>> response = anfrageController.getAnfragenForArzt(2L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(anfragen, response.getBody());
    }

    @Test
    void testCompleteAnfrage() {
        Anfrage anfrage = new Anfrage(new Date(), "photo.jpg", "description", true, 1L, 2L);
        when(anfrageService.completeAnfrage(1L)).thenReturn(anfrage);

        ResponseEntity<Anfrage> response = anfrageController.completeAnfrage(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(anfrage, response.getBody());
    }
}
