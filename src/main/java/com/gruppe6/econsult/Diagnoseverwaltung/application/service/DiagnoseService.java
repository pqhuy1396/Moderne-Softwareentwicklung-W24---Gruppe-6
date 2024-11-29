package com.gruppe6.econsult.Diagnoseverwaltung.application.service;

import com.gruppe6.econsult.Anfragenverwaltung.domain.model.Anfrage;
import com.gruppe6.econsult.Anfragenverwaltung.infrastructure.repository.AnfrageRepository;
import com.gruppe6.econsult.Diagnoseverwaltung.domain.model.Diagnose;
import com.gruppe6.econsult.Diagnoseverwaltung.infrastructure.repository.DiagnoseRepository;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DiagnoseService {

    private final DiagnoseRepository diagnoseRepository;

    private final AnfrageRepository anfrageRepository;

    public DiagnoseService(DiagnoseRepository diagnoseRepository, AnfrageRepository anfrageRepository) {
        this.diagnoseRepository = diagnoseRepository;
        this.anfrageRepository = anfrageRepository;
    }

    // Erstellt eine neue Diagnose, falls die Anfrage existiert
    public Diagnose createDiagnose(Long anfrageId, String diagnoseText) {
        Optional<Anfrage> anfrageOptional = anfrageRepository.findById(anfrageId);
        if (anfrageOptional.isPresent()) {
            Anfrage anfrage = anfrageOptional.get();
            Diagnose diagnose = new Diagnose(anfrage, diagnoseText, new Date(), anfrage.getArztId());
            return diagnoseRepository.save(diagnose); // Speichert die neue Diagnose in der Datenbank
        } else {
            throw new IllegalArgumentException("Anfrage nicht gefunden mit ID: " + anfrageId); // Wirft eine Ausnahme, wenn die Anfrage nicht gefunden wird
        }
    }

    // Ruft eine Diagnose basierend auf der Anfrage-ID ab
    public Diagnose getDiagnoseByAnfrageId(Long anfrageId) {
        return diagnoseRepository.findByAnfrageId(anfrageId).orElse(null); // Gibt die Diagnose oder null zurück, falls nicht gefunden
    }

    // Ruft alle Diagnosen für eine bestimmte Arzt-ID ab
    public List<Diagnose> getDiagnosenForArzt(Long arztId) {
        return diagnoseRepository.findByArztId(arztId); // Gibt die Liste der Diagnosen zurück
    }
}
