package com.gruppe6.econsult.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gruppe6.econsult.model.Diagnose;
import com.gruppe6.econsult.service.DiagnoseService;

import java.util.List;

@RestController
@RequestMapping("/api/diagnosen")
public class DiagnoseController {

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

    @GetMapping("/anfrage/{anfrageId}")
    public ResponseEntity<Diagnose> getDiagnoseByAnfrageId(@PathVariable Long anfrageId) {
        Diagnose diagnose = diagnoseService.getDiagnoseByAnfrageId(anfrageId);
        return diagnose != null ? ResponseEntity.ok(diagnose) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/arzt/{arztId}")
    public ResponseEntity<List<Diagnose>> getDiagnosenForArzt(@PathVariable Long arztId) {
        List<Diagnose> diagnosen = diagnoseService.getDiagnosenForArzt(arztId);
        return ResponseEntity.ok(diagnosen);
    }
}
