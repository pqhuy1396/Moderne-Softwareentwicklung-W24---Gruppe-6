package com.gruppe6.econsult.Diagnoseverwaltung.Application;

import com.gruppe6.econsult.Diagnoseverwaltung.Entity.Diagnose;
import com.gruppe6.econsult.Diagnoseverwaltung.Service.DiagnoseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diagnosen")
public class DiagnoseController {

    @Autowired
    private DiagnoseService diagnoseService;

    // Erstellt eine neue Diagnose und speichert sie in der Datenbank
    @PostMapping("/create")
    public ResponseEntity<Diagnose> createDiagnose(
            @RequestParam Long anfrageId,
            @RequestParam String diagnoseText) {

        try {
            Diagnose diagnose = diagnoseService.createDiagnose(anfrageId, diagnoseText);
            return ResponseEntity.status(HttpStatus.CREATED).body(diagnose); // Gibt HTTP 201 (Created) zurück
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Gibt HTTP 400 (Bad Request) zurück, wenn die Eingabe ungültig ist
        }
    }

    // Ruft eine Diagnose basierend auf der Anfrage-ID ab
    @GetMapping("/anfrage/{anfrageId}")
    public ResponseEntity<Diagnose> getDiagnoseByAnfrageId(@PathVariable Long anfrageId) {
        Diagnose diagnose = diagnoseService.getDiagnoseByAnfrageId(anfrageId);
        return diagnose != null ? ResponseEntity.ok(diagnose) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Gibt HTTP 404 zurück, wenn nicht gefunden
    }

    // Ruft alle Diagnosen für einen bestimmten Arzt ab
    @GetMapping("/arzt/{arztId}")
    public ResponseEntity<List<Diagnose>> getDiagnosenForArzt(@PathVariable Long arztId) {
        List<Diagnose> diagnosen = diagnoseService.getDiagnosenForArzt(arztId);
        return ResponseEntity.ok(diagnosen); // Gibt HTTP 200 mit der Liste der Diagnosen zurück
    }
}
