package com.gruppe6.econsult.Anfragenverwaltung.domain.events;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gruppe6.econsult.Anfragenverwaltung.application.service.AnfrageService;
import com.gruppe6.econsult.Anfragenverwaltung.domain.model.Anfrage;

@RestController
@RequestMapping("/api/anfragen")
public class GetAnfragenForArzt {

    @Autowired
    private AnfrageService anfrageService;

    @GetMapping("/arzt/{arztId}")
    public ResponseEntity<List<Anfrage>> getAnfragenForArzt(@PathVariable Long arztId) {
        List<Anfrage> anfragen = anfrageService.getAnfragenForArzt(arztId);
        return ResponseEntity.ok(anfragen);
    }
}
