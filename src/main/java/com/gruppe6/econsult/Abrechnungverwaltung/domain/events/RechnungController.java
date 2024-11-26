package com.gruppe6.econsult.Abrechnungverwaltung.domain.events;

import java.util.Date;
import java.util.List;
import java.util.UUID;


import com.gruppe6.econsult.Abrechnungverwaltung.domain.model.Rechnung;
import com.gruppe6.econsult.Abrechnungverwaltung.infrastructure.reponsitory.RechnungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        rechnung.setDatum(new Date());
        rechnung.setBetrag(20); 
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