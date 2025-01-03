package com.gruppe6.econsult.Arztverwaltung.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Arzt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Boolean roll; 
    private String fachrichtung;
    private String lizenznummer;
    private String email;
    private String username;
    private String password;

    // Constructors
    public Arzt() {}

    public Arzt(Long id,String name, Boolean roll, String fachrichtung, String lizenznummer, String email, String username, String password) {
        this.name = name;
        this.roll = roll;
        this.fachrichtung = fachrichtung;
        this.lizenznummer = lizenznummer;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRoll() {
        return roll;
    }

    public void setRoll(Boolean roll) {
        this.roll = roll;
    }

    public String getFachrichtung() {
        return fachrichtung;
    }

    public void setFachrichtung(String fachrichtung) {
        this.fachrichtung = fachrichtung;
    }

    public String getLizenznummer() {
        return lizenznummer;
    }

    public void setLizenznummer(String lizenznummer) {
        this.lizenznummer = lizenznummer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
