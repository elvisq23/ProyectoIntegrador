package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.models.Sede;
import com.utp.integradorspringboot.repositories.SedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 

import java.util.List;
import java.util.Optional;

@Service
public class SedeService {

    @Autowired
    private SedeRepository sedeRepository;

    @Transactional(readOnly = true) 
    public List<Sede> getAllSedes() {
        return sedeRepository.findAll();
    }

    @Transactional(readOnly = true) 
    public Optional<Sede> getSedeById(Long id) {
        return sedeRepository.findById(id);
    }
}