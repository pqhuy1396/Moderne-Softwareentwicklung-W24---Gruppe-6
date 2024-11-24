package com.gruppe6.econsult.Anfragenverwaltung.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gruppe6.econsult.Anfragenverwaltung.Entity.Anfrage;
import com.gruppe6.econsult.Arztverwaltung.Entity.Arzt;
import com.gruppe6.econsult.Arztverwaltung.Service.ArztService;
import com.gruppe6.econsult.Patientenverwaltung.Entity.Patient;
import com.gruppe6.econsult.Patientenverwaltung.Service.PatientenService;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

@Service
public class PdfExportAnfrageService {

    @Autowired
    private AnfrageService anfrageService;

    @Autowired
    private ArztService arztService;

    @Autowired
    private PatientenService patientenService;

    public byte[] exportAnfrageToPdf(Long anfrageId) throws IOException {
        Optional<Anfrage> anfrageOpt = anfrageService.getAnfrageById(anfrageId);

        if (anfrageOpt.isEmpty()) {
            throw new IllegalArgumentException("Anfrage not found with ID: " + anfrageId);
        }

        Anfrage anfrage = anfrageOpt.get();
        Optional<Arzt> arztOpt = arztService.getArztById(anfrage.getArztId());
        Optional<Patient> patientOpt = patientenService.getPatientById(anfrage.getPatientId());

        if (arztOpt.isEmpty() || patientOpt.isEmpty()) {
            throw new IllegalArgumentException("Arzt or Patient not found for the Anfrage.");
        }

        Arzt arzt = arztOpt.get();
        Patient patient = patientOpt.get();

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Title
            document.add(new Paragraph("Anfrage Report").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(18));
            document.add(new Paragraph("\n"));

            // Patient Information
            document.add(new Paragraph("Patient Information:").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)));
            document.add(new Paragraph("Name: " + patient.getName()));
            document.add(new Paragraph("Email: " + patient.getEmail()));
            document.add(new Paragraph("Birth Date: " + patient.getBirthDate()));
            document.add(new Paragraph("\n"));

            // Arzt Information
            document.add(new Paragraph("Arzt Information:").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)));
            document.add(new Paragraph("Name: " + arzt.getName()));
            document.add(new Paragraph("Fachrichtung: " + arzt.getFachrichtung()));
            document.add(new Paragraph("Lizenznummer: " + arzt.getLizenznummer()));
            document.add(new Paragraph("\n"));

            // Anfrage Details
            document.add(new Paragraph("Anfrage Details:").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)));
            document.add(new Paragraph("Date: " + anfrage.getDatum()));
            document.add(new Paragraph("Description: " + anfrage.getBeschreibung()));
            document.add(new Paragraph("Status: " + (anfrage.getStatus() ? "Completed" : "Pending")));
            document.add(new Paragraph("\n"));

            document.close();
            return baos.toByteArray();
        }
    }
}
