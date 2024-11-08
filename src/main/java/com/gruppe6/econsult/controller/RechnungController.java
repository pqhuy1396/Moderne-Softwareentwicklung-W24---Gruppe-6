package com.gruppe6.econsult.controller;

import com.gruppe6.econsult.model.Rechnung;
import com.gruppe6.econsult.repository.RechnungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rechnungen")
public class RechnungController {

    @Autowired
    private RechnungRepository rechnungRepository;

    // Endpunkt zum Erstellen einer neuen Rechnung für einen Patienten
    @PostMapping("/create")
    public ResponseEntity<Rechnung> createRechnung(@RequestParam Long idPatient) {
        String rechnungNummer = UUID.randomUUID().toString(); // Generiert eine eindeutige Rechnungsnummer
        Rechnung rechnung = new Rechnung(rechnungNummer, idPatient);

        Rechnung savedRechnung = rechnungRepository.save(rechnung);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRechnung);
    }

    // Endpunkt, um alle Rechnungen eines bestimmten Patienten abzurufen
    @GetMapping("/patient/{idPatient}")
    public ResponseEntity<List<Rechnung>> getRechnungenByPatient(@PathVariable Long idPatient) {
        List<Rechnung> rechnungen = rechnungRepository.findByIdPatient(idPatient);

        if (rechnungen.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(rechnungen);
        }
        return ResponseEntity.ok(rechnungen);
    }
}
