package com.gruppe6.econsult.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gruppe6.econsult.model.Anfrage;
import com.gruppe6.econsult.repository.AnfrageRepository;

@Service
public class AnfrageService {

    @Autowired
    private AnfrageRepository anfrageRepository;

    public Anfrage createAnfrage(Long patientId, Long arztId, String foto, String beschreibung) {
        Anfrage anfrage = new Anfrage(new Date(), foto, beschreibung, false, patientId, arztId); 
        return anfrageRepository.save(anfrage);
    }

    public List<Anfrage> getAnfragenForPatient(Long patientId) {
        return anfrageRepository.findAnfragenByPatientId(patientId);
    }

    public List<Anfrage> getAnfragenForArzt(Long arztId) {
        return anfrageRepository.findAnfragenByArztId(arztId);
    }
    
     public Optional<Anfrage> getAnfrageById(Long anfrageId) {
        return anfrageRepository.findById(anfrageId);
    }

    public Anfrage completeAnfrage(Long anfrageId) {
        Optional<Anfrage> anfrageOptional = anfrageRepository.findById(anfrageId);
        if (anfrageOptional.isPresent()) {
            Anfrage anfrage = anfrageOptional.get();
            anfrage.setStatus(true); 
            return anfrageRepository.save(anfrage);
        } else {
            throw new IllegalArgumentException("Anfrage not found with ID: " + anfrageId);
        }
    }
}
