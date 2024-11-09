package com.gruppe6.econsult.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gruppe6.econsult.model.Arzt;
import com.gruppe6.econsult.repository.ArztRepository;

@Service
public class ArztService {

    @Autowired
    private ArztRepository arztRepository;

    public Optional<Arzt> getArztById(Long id) {
        return arztRepository.findArztById(id);
    }
    
     public List<Arzt> getAllArzt() {
        return arztRepository.findAll();
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

    public Optional<Arzt> getArztByEmail(String email) {
        return arztRepository.findByEmail(email);
    }

    public Long generateRandomId() {
        return 1000000000L + new Random().nextLong() % 9000000000L;
    }
}
