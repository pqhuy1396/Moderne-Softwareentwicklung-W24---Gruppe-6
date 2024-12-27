package com.gruppe6.econsult.Anfragenverwaltung.domain.events;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gruppe6.econsult.Anfragenverwaltung.application.service.AnfrageService;
import com.gruppe6.econsult.Anfragenverwaltung.domain.model.Anfrage;

@RestController
@RequestMapping("/api/anfragen")
public class GetAnfragenForPatient {

    @Autowired
    private AnfrageService anfrageService;

    private static final String PHOTO_BASE_URL = "http://localhost:8080/public/photo/";

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Anfrage>> getAnfragenForPatient(@PathVariable Long patientId) {
        List<Anfrage> anfragen = anfrageService.getAnfragenForPatient(patientId)
                .stream()
                .map(anfrage -> {
                    if (anfrage.getFoto() != null && !anfrage.getFoto().isEmpty()) {
                        // Append full URL to the image filename
                        anfrage.setFoto(PHOTO_BASE_URL + anfrage.getFoto());
                    }
                    return anfrage;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(anfragen);
    }
}