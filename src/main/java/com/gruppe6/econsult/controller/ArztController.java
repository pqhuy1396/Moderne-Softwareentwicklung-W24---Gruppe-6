package com.gruppe6.econsult.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gruppe6.econsult.model.Arzt;
import com.gruppe6.econsult.service.ArztService;

@RestController
@RequestMapping("/api/arzt")
public class ArztController {

    @Autowired
    private ArztService arztService;

    // Get a list of all Ärzte
    @GetMapping("/all")
    public ResponseEntity<List<Arzt>> getAllArzt() {
        List<Arzt> arzt = arztService.getAllArzt();
        return ResponseEntity.ok(arzt);
    }

    // Get an Arzt by ID
    @GetMapping("/{id}")
    public ResponseEntity<Arzt> getArztById(@PathVariable Long id) {
        Optional<Arzt> arztOpt = arztService.getArztById(id);
        return arztOpt.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Get an Arzt by username
    @GetMapping("/username/{username}")
    public ResponseEntity<Arzt> getArztByUsername(@PathVariable String username) {
        Optional<Arzt> arztOpt = arztService.getArztByUsername(username);
        return arztOpt.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Get an Arzt by email
    @GetMapping("/email/{email}")
    public ResponseEntity<Arzt> getArztByEmail(@PathVariable String email) {
        Optional<Arzt> arztOpt = arztService.getArztByEmail(email);
        return arztOpt.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Save or update an Arzt
    @PostMapping("/save")
    public ResponseEntity<Arzt> saveArzt(@RequestBody Arzt arzt) {
        Arzt savedArzt = arztService.saveArzt(arzt);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArzt);
    }

    // Delete an Arzt by ID
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
