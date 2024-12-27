package com.gruppe6.econsult.Arztverwaltung.domain.events;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gruppe6.econsult.Arztverwaltung.application.service.ArztService;
import com.gruppe6.econsult.Arztverwaltung.domain.model.Arzt;

@RestController
@RequestMapping("/api/arzt")
public class GetAllArzt {

    @Autowired
    private ArztService arztService;

    @GetMapping("/all")
    public ResponseEntity<List<Arzt>> getAllArzt() {
        List<Arzt> arzt = arztService.getAllArzt();
        return ResponseEntity.ok(arzt);
    }
}
