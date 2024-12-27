package com.gruppe6.econsult.Arztverwaltung.domain.events;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gruppe6.econsult.Arztverwaltung.application.service.ArztService;
import com.gruppe6.econsult.Arztverwaltung.domain.model.Arzt;

@RestController
@RequestMapping("/api/arzt")
public class GetArztById {

    @Autowired
    private ArztService arztService;

    @GetMapping("/{id}")
    public ResponseEntity<Arzt> getArztById(@PathVariable Long id) {
        Optional<Arzt> arztOpt = arztService.getArztById(id);
        return arztOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}