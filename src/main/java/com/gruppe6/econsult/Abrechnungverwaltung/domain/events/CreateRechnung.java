package com.gruppe6.econsult.Abrechnungverwaltung.domain.events;

import com.gruppe6.econsult.Abrechnungverwaltung.domain.model.Rechnung;
import com.gruppe6.econsult.Abrechnungverwaltung.infrastructure.reponsitory.RechnungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/rechnungen")
public class CreateRechnung {

    @Autowired
    private RechnungRepository rechnungRepository;

    @PostMapping("/create")
    public ResponseEntity<Rechnung> createRechnung(@RequestParam Long idPatient) {
        String rechnungNummer = UUID.randomUUID().toString(); // Generate unique invoice number
        Rechnung rechnung = new Rechnung(rechnungNummer, idPatient);
        rechnung.setDatum(new Date());
        rechnung.setBetrag(20);
        Rechnung savedRechnung = rechnungRepository.save(rechnung);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRechnung);
    }
}