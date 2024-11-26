package com.gruppe6.econsult.Arztverwaltung.infrastructure.repository;

import java.util.Optional;

import com.gruppe6.econsult.Arztverwaltung.domain.model.Arzt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArztRepository extends JpaRepository<Arzt, Long> {
    Optional<Arzt> findByUsername(String username);
    Optional<Arzt> findArztById(Long id);
    Optional<Arzt> findByEmail(String email);
    void deleteArztById(Long id);
}
