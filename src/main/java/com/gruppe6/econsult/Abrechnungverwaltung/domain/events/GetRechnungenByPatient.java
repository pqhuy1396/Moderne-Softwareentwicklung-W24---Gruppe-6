package com.gruppe6.econsult.Abrechnungverwaltung.domain.events;

import com.gruppe6.econsult.Abrechnungverwaltung.domain.model.Rechnung;
import com.gruppe6.econsult.Abrechnungverwaltung.infrastructure.reponsitory.RechnungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rechnungen")
public class GetRechnungenByPatient {

    @Autowired
    private RechnungRepository rechnungRepository;

    @GetMapping("/patient/{idPatient}")
    public ResponseEntity<List<Rechnung>> getRechnungenByPatient(@PathVariable Long idPatient) {
        List<Rechnung> rechnungen = rechnungRepository.findByIdPatient(idPatient);

        if (rechnungen.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(rechnungen);
        }
        return ResponseEntity.ok(rechnungen);
    }
}