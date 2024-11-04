package com.gruppe6.econsult.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gruppe6.econsult.model.Anfrage;
import com.gruppe6.econsult.service.AnfrageService;

@RestController
@RequestMapping("/api/anfragen")
public class AnfrageController {

    @Autowired
    private AnfrageService anfrageService;

    @PostMapping("/create")
    public ResponseEntity<Anfrage> createAnfrage(
            @RequestParam Long patientId,
            @RequestParam Long arztId,
            @RequestParam String foto,
            @RequestParam String beschreibung) {

        try {
            Anfrage anfrage = anfrageService.createAnfrage(patientId, arztId, foto, beschreibung);
            return ResponseEntity.status(HttpStatus.CREATED).body(anfrage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/arzt/{arztId}")
    public ResponseEntity<List<Anfrage>> getAnfragenForArzt(@PathVariable Long arztId) {
        List<Anfrage> anfragen = anfrageService.getAnfragenForArzt(arztId);
        return ResponseEntity.ok(anfragen);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Anfrage>> getAnfragenForPatient(@PathVariable Long patientId) {
        List<Anfrage> anfragen = anfrageService.getAnfragenForPatient(patientId);
        return ResponseEntity.ok(anfragen);
    }

    @PutMapping("/complete/{anfrageId}")
    public ResponseEntity<Anfrage> completeAnfrage(@PathVariable Long anfrageId) {
        try {
            Anfrage completedAnfrage = anfrageService.completeAnfrage(anfrageId);
            return ResponseEntity.ok(completedAnfrage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}