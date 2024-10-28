package com.gruppe6.econsult.service;

import com.gruppe6.econsult.model.Arzt;
import com.gruppe6.econsult.repository.ArztRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArztService {

    @Autowired
    private ArztRepository arztRepository;

    public Optional<Arzt> getArztById(Long id) {
        return arztRepository.findArztById(id);
    }

    public Arzt saveArzt(Arzt arzt) {
        return arztRepository.save(arzt);
    }

    public void deleteArztById(Long id) {
        arztRepository.deleteArztById(id);
    }

    public Optional<Arzt> getArztByUsername(String username) {
        return arztRepository.findByUsername(username);
    }
}
