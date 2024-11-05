package com.gruppe6.econsult.repository;

import com.gruppe6.econsult.model.Diagnose;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiagnoseRepository extends JpaRepository<Diagnose, Long> {
    Optional<Diagnose> findByAnfrageId(Long anfrageId);
    List<Diagnose> findByArztId(Long arztId);
}
