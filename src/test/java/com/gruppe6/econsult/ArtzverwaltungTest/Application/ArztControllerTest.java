package com.gruppe6.econsult.ArtzverwaltungTest.Application;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gruppe6.econsult.Arztverwaltung.Application.ArztController;
import com.gruppe6.econsult.Arztverwaltung.Entity.Arzt;
import com.gruppe6.econsult.Arztverwaltung.Service.ArztService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



class ArztControllerTest {

    @Mock
    private ArztService arztService;

    @InjectMocks
    private ArztController arztController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllArzt_case1() {
        List<Arzt> arztList = Arrays.asList(
            new Arzt(1L, "Dr. Max Mustermann", true, "Dermatology", "12345", "Mustermann@example.com", "Mustermann_Max", "password"),
            new Arzt(2L, "Dr. Jane Smith", true, "Cardiology", "67890", "jane.smith@example.com", "jane_smith", "password")
        );
        
        when(arztService.getAllArzt()).thenReturn(arztList);
        
        ResponseEntity<List<Arzt>> response = arztController.getAllArzt();
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getArztById_case1() {
        Arzt arzt = new Arzt(1L, "Dr. Max Mustermann", true, "Dermatology", "12345", "Mustermann@example.com", "Mustermann_Max", "password");
        
        when(arztService.getArztById(1L)).thenReturn(Optional.of(arzt));
        
        ResponseEntity<Arzt> response = arztController.getArztById(1L);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(arzt, response.getBody());
    }

    @Test
    void getArztById_case2() {
        when(arztService.getArztById(anyLong())).thenReturn(Optional.empty());
        
        ResponseEntity<Arzt> response = arztController.getArztById(1L);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getArztByUsername_case1() {
        Arzt arzt = new Arzt(1L, "Dr. Max Mustermann", true, "Dermatology", "12345", "Mustermann@example.com", "Mustermann_Max", "password");
        
        when(arztService.getArztByUsername("Mustermann_Max")).thenReturn(Optional.of(arzt));
        
        ResponseEntity<Arzt> response = arztController.getArztByUsername("Mustermann_Max");
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(arzt, response.getBody());
    }

    @Test
    void getArztByUsername_case2() {
        when(arztService.getArztByUsername(anyString())).thenReturn(Optional.empty());
        
        ResponseEntity<Arzt> response = arztController.getArztByUsername("Mustermann_Max");
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getArztByEmail_case1() {
        Arzt arzt = new Arzt(1L, "Dr. Max Mustermann", true, "Dermatology", "12345", "Mustermann@example.com", "Mustermann_Max", "password");
        
        when(arztService.getArztByEmail("Mustermann@example.com")).thenReturn(Optional.of(arzt));
        
        ResponseEntity<Arzt> response = arztController.getArztByEmail("Mustermann@example.com");
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(arzt, response.getBody());
    }

    @Test
    void getArztByEmail_case2() {
        when(arztService.getArztByEmail(anyString())).thenReturn(Optional.empty());
        
        ResponseEntity<Arzt> response = arztController.getArztByEmail("Mustermann@example.com");
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void saveArzt_case1() {
        Arzt arzt = new Arzt(1L, "Dr. Max Mustermann", true, "Dermatology", "12345", "Mustermann@example.com", "Mustermann_Max", "password");
        
        when(arztService.saveArzt(any(Arzt.class))).thenReturn(arzt);
        
        ResponseEntity<Arzt> response = arztController.saveArzt(arzt);
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(arzt, response.getBody());
    }

    @Test
    void deleteArztById_case1() {
        Arzt arzt = new Arzt(1L, "Dr. Max Mustermann", true, "Dermatology", "12345", "Mustermann@example.com", "Mustermann_Max", "password");
        
        when(arztService.getArztById(1L)).thenReturn(Optional.of(arzt));
        doNothing().when(arztService).deleteArztById(1L);
        
        ResponseEntity<Void> response = arztController.deleteArztById(1L);
        
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteArztById_case2() {
        when(arztService.getArztById(anyLong())).thenReturn(Optional.empty());
        
        ResponseEntity<Void> response = arztController.deleteArztById(1L);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
