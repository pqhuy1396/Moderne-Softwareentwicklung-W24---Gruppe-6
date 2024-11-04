package com.gruppe6.econsult.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Anfrage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date datum;
    private String foto;
    private String beschreibung;
    private boolean status;

    private Long patientId;
    private Long arztId;

    // Constructors
    public Anfrage() {
    }

    public Anfrage(Date datum, String foto, String beschreibung, boolean status, Long patientId, Long arztId) {
        this.datum = datum;
        this.foto = foto;
        this.beschreibung = beschreibung;
        this.status = status;
        this.patientId = patientId;
        this.arztId = arztId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getArztId() {
        return arztId;
    }

    public void setArztId(Long arztId) {
        this.arztId = arztId;
    }
}
