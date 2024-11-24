package com.gruppe6.econsult.Anfragenverwaltung.Repository;

import java.util.List;
import java.util.Optional;

import com.gruppe6.econsult.Anfragenverwaltung.Entity.Anfrage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AnfrageRepository extends JpaRepository<Anfrage, Long> {
    List<Anfrage> findAnfragenByPatientId(Long patientId);
    List<Anfrage> findAnfragenByArztId(Long arztId);
    Optional<Anfrage> findById(Long id);
}
