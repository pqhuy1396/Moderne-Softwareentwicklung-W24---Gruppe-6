package com.gruppe6.econsult.Anfragenverwaltung.domain.events;

import java.io.IOException;
import java.util.List;

import com.gruppe6.econsult.Anfragenverwaltung.application.service.AnfrageService;
import com.gruppe6.econsult.Anfragenverwaltung.application.service.PdfExportAnfrageService;
import com.gruppe6.econsult.Anfragenverwaltung.domain.model.Anfrage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/anfragen")
public class AnfrageController {

    @Autowired
    private AnfrageService anfrageService;
    private PdfExportAnfrageService PdfExportAnfrageService;

    @PostMapping("/create")
    public ResponseEntity<Anfrage> createAnfrage(
            @RequestParam Long patientId,
            @RequestParam Long arztId,
            @RequestParam String foto,
            @RequestParam String beschreibung) {
        if (patientId == null || arztId == null || beschreibung == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            try {
                Anfrage anfrage = anfrageService.createAnfrage(patientId, arztId, foto, beschreibung);
                return ResponseEntity.status(HttpStatus.CREATED).body(anfrage);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }

    }

    @GetMapping("/arzt/{arztId}")
    public ResponseEntity<List<Anfrage>> getAnfragenForArzt(@PathVariable Long arztId) {
        List<Anfrage> anfragen = anfrageService.getAnfragenForArzt(arztId);
        return ResponseEntity.ok(anfragen);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Anfrage>> getAnfragenForPatient(@PathVariable Long patientId) {
        List<Anfrage> anfragen = anfrageService.getAnfragenForPatient(patientId);
        return ResponseEntity.ok(anfragen);
    }

    @GetMapping("/export/{anfrageId}")
    public ResponseEntity<byte[]> exportAnfrageToPdf(@PathVariable Long anfrageId) throws IOException {
        if (anfrageId == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        try {
            byte[] pdfData = PdfExportAnfrageService.exportAnfrageToPdf(anfrageId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "anfrage_report.pdf");

            return ResponseEntity.ok().headers(headers).body(pdfData);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/complete/{anfrageId}")
    public ResponseEntity<Anfrage> completeAnfrage(@PathVariable Long anfrageId) {
        if (anfrageId == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        try {
            Anfrage completedAnfrage = anfrageService.completeAnfrage(anfrageId);
            return ResponseEntity.ok(completedAnfrage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
