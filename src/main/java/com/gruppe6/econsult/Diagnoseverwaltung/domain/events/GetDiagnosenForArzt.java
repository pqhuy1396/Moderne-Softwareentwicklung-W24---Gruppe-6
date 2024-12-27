package com.gruppe6.econsult.Diagnoseverwaltung.domain.events;

import com.gruppe6.econsult.Diagnoseverwaltung.application.service.DiagnoseService;
import com.gruppe6.econsult.Diagnoseverwaltung.domain.model.Diagnose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/diagnosen")
public class GetDiagnosenForArzt {

    @Autowired
    private DiagnoseService diagnoseService;

    @GetMapping("/arzt/{arztId}")
    public ResponseEntity<List<Diagnose>> getDiagnosenForArzt(@PathVariable Long arztId) {
        List<Diagnose> diagnosen = diagnoseService.getDiagnosenForArzt(arztId);
        return ResponseEntity.ok(diagnosen);
    }
}
