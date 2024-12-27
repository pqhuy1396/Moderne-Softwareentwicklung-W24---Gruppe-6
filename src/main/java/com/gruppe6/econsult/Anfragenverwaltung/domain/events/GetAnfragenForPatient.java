package com.gruppe6.econsult.Anfragenverwaltung.domain.events;

import java.util.List;

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

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Anfrage>> getAnfragenForPatient(@PathVariable Long patientId) {
        List<Anfrage> anfragen = anfrageService.getAnfragenForPatient(patientId);
        return ResponseEntity.ok(anfragen);
    }
}
