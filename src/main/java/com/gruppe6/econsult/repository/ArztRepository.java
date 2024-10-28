package com.gruppe6.econsult.repository;

import com.gruppe6.econsult.model.Arzt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ArztRepository extends JpaRepository<Arzt, Long> {
    Optional<Arzt> findByUsername(String username);
    Optional<Arzt> findArztById(Long id);
    void deleteArztById(Long id);
}
