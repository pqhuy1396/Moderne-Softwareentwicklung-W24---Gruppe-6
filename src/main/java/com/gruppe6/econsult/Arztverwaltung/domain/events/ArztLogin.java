package com.gruppe6.econsult.Arztverwaltung.domain.events;

import java.util.Optional;

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
public class ArztLogin {

    @Autowired
    private ArztService arztService;

    @PostMapping("/login")
    public ResponseEntity<Arzt> login(@RequestParam String username, @RequestParam String password) {
        Optional<Arzt> arzt = arztService.getArztByUsername(username);
        if (arzt.isPresent() && arzt.get().getPassword().equals(password)) {
            if (arzt.get().getRoll()) {
                return ResponseEntity.ok(arzt.get());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}