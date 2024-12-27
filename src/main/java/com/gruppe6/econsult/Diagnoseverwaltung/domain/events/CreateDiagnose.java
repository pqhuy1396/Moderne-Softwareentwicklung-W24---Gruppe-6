package com.gruppe6.econsult.Diagnoseverwaltung.domain.events;

import com.gruppe6.econsult.Diagnoseverwaltung.application.service.DiagnoseService;
import com.gruppe6.econsult.Diagnoseverwaltung.domain.model.Diagnose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/diagnosen")
public class CreateDiagnose {

    @Autowired
    private DiagnoseService diagnoseService;

    @PostMapping("/create")
    public ResponseEntity<Diagnose> createDiagnose(
            @RequestParam Long anfrageId,
            @RequestParam String diagnoseText) {

        try {
            Diagnose diagnose = diagnoseService.createDiagnose(anfrageId, diagnoseText);
            return ResponseEntity.status(HttpStatus.CREATED).body(diagnose);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
