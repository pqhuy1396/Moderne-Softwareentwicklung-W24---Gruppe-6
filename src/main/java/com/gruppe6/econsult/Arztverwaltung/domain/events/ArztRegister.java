package com.gruppe6.econsult.Arztverwaltung.domain.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gruppe6.econsult.Arztverwaltung.application.service.ArztService;
import com.gruppe6.econsult.Arztverwaltung.domain.model.Arzt;

@RestController
@RequestMapping("/api/arzt")
public class ArztRegister {

    @Autowired
    private ArztService arztService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String fachrichtung,
            @RequestParam String lizenznummer
    ) {
        if (fachrichtung == null || lizenznummer == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing fachrichtung or lizenznummer for Arzt");
        }
        Long id = arztService.generateRandomId();
        Arzt newArzt = new Arzt(id, name, true, fachrichtung, lizenznummer, email, username, password);
        arztService.saveArzt(newArzt);
        return ResponseEntity.status(HttpStatus.CREATED).body("Arzt registered successfully");
    }
}
