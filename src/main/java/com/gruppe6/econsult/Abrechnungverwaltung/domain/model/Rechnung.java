package com.gruppe6.econsult.Abrechnungverwaltung.domain.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Rechnung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date datum;
    private String rechnungNummer; 
    private Long idPatient; 
    private double betrag;

    // Konstruktoren
    public Rechnung() {}

    public Rechnung(String rechnungNummer, Long idPatient) {
        this.datum = new Date(); // aktuelles Datum
        this.rechnungNummer = rechnungNummer;
        this.idPatient = idPatient;
        this.betrag = betrag; // Festgelegter Betrag pro Diagnose
    }

    // Getter und Setter
     public Long getId() {
        return id;
    }
    public Date getDatum() { return datum; }
    public String getRechnungNummer() { return rechnungNummer; }
    public Long getIdPatient() { return idPatient; }
    public double getBetrag() { return betrag; }

    public void setDatum(Date datum) { this.datum = datum; }
    public void setRechnungNummer(String rechnungNummer) { this.rechnungNummer = rechnungNummer; }
    public void setIdPatient(Long idPatient) { this.idPatient = idPatient; }
    public void setId(Long id) {
        this.id = id;
    }

    public void setBetrag(double betrag) {
        this.betrag = betrag;
    }
}
