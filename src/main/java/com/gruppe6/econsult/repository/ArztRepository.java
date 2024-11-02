package com.gruppe6.econsult.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gruppe6.econsult.model.Arzt;

@Repository
public interface ArztRepository extends JpaRepository<Arzt, Long> {
    Optional<Arzt> findByUsername(String username);
    Optional<Arzt> findArztById(Long id);
    Optional<Arzt> findByEmail(String email);
    void deleteArztById(Long id);
}
