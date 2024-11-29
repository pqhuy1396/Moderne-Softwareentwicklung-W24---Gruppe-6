package com.gruppe6.econsult.Abrechnungverwaltung.application.service;

import com.gruppe6.econsult.Abrechnungverwaltung.domain.model.Rechnung;
import com.gruppe6.econsult.Abrechnungverwaltung.infrastructure.reponsitory.RechnungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



@Service
public class RechnungService {

    private final RechnungRepository rechnungRepository;

    @Autowired
    public RechnungService(RechnungRepository rechnungRepository) {
        this.rechnungRepository = rechnungRepository;
    }

    /**
     * Erstellt eine neue Rechnung für einen bestimmten Patienten
     * und speichert sie in der Datenbank.
     *
     * @param patientId Die ID des Patienten, für den die Rechnung erstellt wird.
     * @return Die gespeicherte Rechnung.
     */
    public Rechnung createRechnung(Long patientId) {
        String rechnungNummer = UUID.randomUUID().toString(); // Generiert eine eindeutige Rechnungsnummer
        Rechnung rechnung = new Rechnung(rechnungNummer, patientId);
        rechnung.setDatum(new Date()); // Setzt das aktuelle Datum
        rechnung.setBetrag(20); // Setzt den festen Betrag
        return rechnungRepository.save(rechnung);
    }

    /**
     * Gibt eine Liste aller Rechnungen für einen bestimmten Patienten zurück.
     *
     * @param patientId Die ID des Patienten.
     * @return Eine Liste von Rechnungen, die dem Patienten zugeordnet sind.
     */
    public List<Rechnung> getRechnungenForPatient(Long patientId) {
        return rechnungRepository.findByIdPatient(patientId);
    }

    /**
     * Findet eine spezifische Rechnung anhand ihrer ID.
     *
     * @param rechnungId Die ID der Rechnung.
     * @return Die gefundene Rechnung, falls vorhanden.
     */
    public Optional<Rechnung> getRechnungById(Long rechnungId) {
        return rechnungRepository.findById(rechnungId);
    }

    /**
     * Löscht eine Rechnung anhand ihrer ID.
     *
     * @param rechnungId Die ID der zu löschenden Rechnung.
     */
    public void deleteRechnung(Long rechnungId) {
        rechnungRepository.deleteById(rechnungId);
    }
}
