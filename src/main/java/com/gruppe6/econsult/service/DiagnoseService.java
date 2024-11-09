package com.gruppe6.econsult.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gruppe6.econsult.model.Anfrage;
import com.gruppe6.econsult.model.Diagnose;
import com.gruppe6.econsult.repository.AnfrageRepository;
import com.gruppe6.econsult.repository.DiagnoseRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DiagnoseService {

    @Autowired
    private DiagnoseRepository diagnoseRepository;

    @Autowired
    private AnfrageRepository anfrageRepository;

    public Diagnose createDiagnose(Long anfrageId, String diagnoseText) {
        Optional<Anfrage> anfrageOptional = anfrageRepository.findById(anfrageId);
        if (anfrageOptional.isPresent()) {
            Anfrage anfrage = anfrageOptional.get();
            Diagnose diagnose = new Diagnose(anfrage, diagnoseText, new Date(), anfrage.getArztId());
            return diagnoseRepository.save(diagnose);
        } else {
            throw new IllegalArgumentException("Anfrage not found with ID: " + anfrageId);
        }
    }

    public Diagnose getDiagnoseByAnfrageId(Long anfrageId) {
        return diagnoseRepository.findByAnfrageId(anfrageId).orElse(null);
    }

    public List<Diagnose> getDiagnosenForArzt(Long arztId) {
        return diagnoseRepository.findByArztId(arztId);
    }
}
