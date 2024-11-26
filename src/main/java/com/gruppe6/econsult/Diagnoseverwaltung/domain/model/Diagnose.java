package com.gruppe6.econsult.Diagnoseverwaltung.domain.model;

import com.gruppe6.econsult.Anfragenverwaltung.domain.model.Anfrage;
import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Diagnose {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "anfrage_id", nullable = false)
    private Anfrage anfrage; // Verknüpft die Diagnose mit einer Anfrage

    private String diagnoseText; // Speichert den Text der Diagnose
    private Date erstelltAm; // Speichert das Erstellungsdatum

    private Long arztId; // Identifiziert den zugehörigen Arzt

    // Standardkonstruktor
    public Diagnose() {
    }

    // Konstruktor mit Parametern
    public Diagnose(Anfrage anfrage, String diagnoseText, Date erstelltAm, Long arztId) {
        this.anfrage = anfrage;
        this.diagnoseText = diagnoseText;
        this.erstelltAm = erstelltAm;
        this.arztId = arztId;
    }

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Anfrage getAnfrage() {
        return anfrage;
    }

    public void setAnfrage(Anfrage anfrage) {
        this.anfrage = anfrage;
    }

    public String getDiagnoseText() {
        return diagnoseText;
    }

    public void setDiagnoseText(String diagnoseText) {
        this.diagnoseText = diagnoseText;
    }

    public Date getErstelltAm() {
        return erstelltAm;
    }

    public void setErstelltAm(Date erstelltAm) {
        this.erstelltAm = erstelltAm;
    }

    public Long getArztId() {
        return arztId;
    }

    public void setArztId(Long arztId) {
        this.arztId = arztId;
    }
}
