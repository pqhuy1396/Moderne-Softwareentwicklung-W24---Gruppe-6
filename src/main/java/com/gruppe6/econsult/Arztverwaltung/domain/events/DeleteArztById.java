package com.gruppe6.econsult.Arztverwaltung.domain.events;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gruppe6.econsult.Arztverwaltung.application.service.ArztService;
import com.gruppe6.econsult.Arztverwaltung.domain.model.Arzt;

@RestController
@RequestMapping("/api/arzt")
public class DeleteArztById {

    @Autowired
    private ArztService arztService;

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteArztById(@PathVariable Long id) {
        Optional<Arzt> arztOpt = arztService.getArztById(id);
        if (arztOpt.isPresent()) {
            arztService.deleteArztById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
