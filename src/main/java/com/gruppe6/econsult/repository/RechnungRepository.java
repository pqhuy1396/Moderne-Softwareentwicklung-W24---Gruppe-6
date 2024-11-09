package com.gruppe6.econsult.repository;

import com.gruppe6.econsult.model.Rechnung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RechnungRepository extends JpaRepository<Rechnung, Long> {
    List<Rechnung> findByIdPatient(Long idPatient); // Alle Rechnungen eines Patienten abrufen
}
