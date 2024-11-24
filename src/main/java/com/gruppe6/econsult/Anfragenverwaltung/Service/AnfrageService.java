package com.gruppe6.econsult.Anfragenverwaltung.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.gruppe6.econsult.Anfragenverwaltung.Entity.Anfrage;
import com.gruppe6.econsult.Anfragenverwaltung.Repository.AnfrageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



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
