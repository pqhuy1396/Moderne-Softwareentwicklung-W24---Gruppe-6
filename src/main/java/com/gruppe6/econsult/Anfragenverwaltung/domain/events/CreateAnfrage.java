package com.gruppe6.econsult.Anfragenverwaltung.domain.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gruppe6.econsult.Anfragenverwaltung.application.service.AnfrageService;
import com.gruppe6.econsult.Anfragenverwaltung.domain.model.Anfrage;

@RestController
@RequestMapping("/api/anfragen")
public class CreateAnfrage {

    @Autowired
    private AnfrageService anfrageService;

    @PostMapping("/create")
    public ResponseEntity<Anfrage> createAnfrage(
            @RequestParam Long patientId,
            @RequestParam Long arztId,
            @RequestParam String foto,
            @RequestParam String beschreibung) {
        if (patientId == null || arztId == null || beschreibung == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            try {
                Anfrage anfrage = anfrageService.createAnfrage(patientId, arztId, foto, beschreibung);
                return ResponseEntity.status(HttpStatus.CREATED).body(anfrage);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }
    }
}