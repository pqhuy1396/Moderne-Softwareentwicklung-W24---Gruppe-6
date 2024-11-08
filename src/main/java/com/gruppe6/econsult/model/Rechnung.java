package com.gruppe6.econsult.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;

@Entity
public class Rechnung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date datum;
    private String rechnungNummer; // Eindeutige Rechnungsnummer
    private Long idPatient; // Verknüpfung zum Patienten
    private double betrag;

    // Konstruktoren
    public Rechnung() {}

    public Rechnung(String rechnungNummer, Long idPatient) {
        this.datum = new Date(); // aktuelles Datum
        this.rechnungNummer = rechnungNummer;
        this.idPatient = idPatient;
        this.betrag = 20.0; // Festgelegter Betrag pro Diagnose
    }

    // Getter und Setter
    public Date getDatum() { return datum; }
    public String getRechnungNummer() { return rechnungNummer; }
    public Long getIdPatient() { return idPatient; }
    public double getBetrag() { return betrag; }

    public void setDatum(Date datum) { this.datum = datum; }
    public void setRechnungNummer(String rechnungNummer) { this.rechnungNummer = rechnungNummer; }
    public void setIdPatient(Long idPatient) { this.idPatient = idPatient; }
}
