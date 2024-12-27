package com.gruppe6.econsult.Anfragenverwaltung.domain.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gruppe6.econsult.Anfragenverwaltung.application.service.PdfExportAnfrageService;

import java.io.IOException;

@RestController
@RequestMapping("/api/anfragen")
public class ExportAnfrageToPdf {

    @Autowired
    private PdfExportAnfrageService pdfExportAnfrageService;

    @GetMapping("/export/{anfrageId}")
    public ResponseEntity<byte[]> exportAnfrageToPdf(@PathVariable Long anfrageId) throws IOException {
        if (anfrageId == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        try {
            byte[] pdfData = pdfExportAnfrageService.exportAnfrageToPdf(anfrageId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "anfrage_report.pdf");

            return ResponseEntity.ok().headers(headers).body(pdfData);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}