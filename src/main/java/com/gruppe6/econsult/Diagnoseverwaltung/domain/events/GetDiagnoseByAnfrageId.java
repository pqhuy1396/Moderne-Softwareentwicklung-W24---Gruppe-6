package com.gruppe6.econsult.Diagnoseverwaltung.domain.events;

import com.gruppe6.econsult.Diagnoseverwaltung.application.service.DiagnoseService;
import com.gruppe6.econsult.Diagnoseverwaltung.domain.model.Diagnose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/diagnosen")
public class GetDiagnoseByAnfrageId {

    @Autowired
    private DiagnoseService diagnoseService;

    @GetMapping("/anfrage/{anfrageId}")
    public ResponseEntity<Diagnose> getDiagnoseByAnfrageId(@PathVariable Long anfrageId) {
        Diagnose diagnose = diagnoseService.getDiagnoseByAnfrageId(anfrageId);
        return diagnose != null ? ResponseEntity.ok(diagnose) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
