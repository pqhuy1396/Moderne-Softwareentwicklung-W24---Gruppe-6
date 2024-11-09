package com.gruppe6.econsult.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gruppe6.econsult.model.Anfrage;

@Repository
public interface AnfrageRepository extends JpaRepository<Anfrage, Long> {
    List<Anfrage> findAnfragenByPatientId(Long patientId);
    List<Anfrage> findAnfragenByArztId(Long arztId);
    Optional<Anfrage> findById(Long id);
}
