package com.gruppe6.econsult.Anfragenverwaltung.domain.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gruppe6.econsult.Anfragenverwaltung.application.service.AnfrageService;
import com.gruppe6.econsult.Anfragenverwaltung.domain.model.Anfrage;

@RestController
@RequestMapping("/api/anfragen")
public class CompleteAnfrage {

    @Autowired
    private AnfrageService anfrageService;

    @PutMapping("/complete/{anfrageId}")
    public ResponseEntity<Anfrage> completeAnfrage(@PathVariable Long anfrageId) {
        if (anfrageId == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        try {
            Anfrage completedAnfrage = anfrageService.completeAnfrage(anfrageId);
            return ResponseEntity.ok(completedAnfrage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
