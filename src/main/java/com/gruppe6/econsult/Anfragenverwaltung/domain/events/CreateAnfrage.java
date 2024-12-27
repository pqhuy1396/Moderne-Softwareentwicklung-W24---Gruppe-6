package com.gruppe6.econsult.Anfragenverwaltung.domain.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.gruppe6.econsult.Anfragenverwaltung.application.service.AnfrageService;
import com.gruppe6.econsult.Anfragenverwaltung.domain.model.Anfrage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/anfragen")
public class CreateAnfrage {

    @Autowired
    private AnfrageService anfrageService;

    private static final String PHOTO_DIRECTORY = "public/photo/";

    @PostMapping("/create")
    public ResponseEntity<Anfrage> createAnfrage(
            @RequestParam Long patientId,
            @RequestParam Long arztId,
            @RequestParam MultipartFile foto,
            @RequestParam String beschreibung) {

        if (patientId == null || arztId == null || beschreibung == null || foto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        try {
            // Save the photo to the server
            String fileName = System.currentTimeMillis() + "_" + foto.getOriginalFilename();
            Path filePath = Paths.get(PHOTO_DIRECTORY + fileName);

            File directory = new File(PHOTO_DIRECTORY);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            Files.copy(foto.getInputStream(), filePath);

            // Save the photo filename to the database
            Anfrage anfrage = anfrageService.createAnfrage(patientId, arztId, fileName, beschreibung);

            return ResponseEntity.status(HttpStatus.CREATED).body(anfrage);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
