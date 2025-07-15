package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.models.Rol;
import com.utp.integradorspringboot.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 

import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    @Transactional(readOnly = true) 
    public List<Rol> getAllRoles() {
        return rolRepository.findAll();
    }

    @Transactional(readOnly = true) 
    public Optional<Rol> getRolById(Long id) {
        return rolRepository.findById(id);
    }

    @Transactional(readOnly = true) 
    public Optional<Rol> getRolByNombre(String nombre) {
        return rolRepository.findByNombre(nombre);
    }

    @Transactional(readOnly = true) 
    public List<Rol> getRolesByNames(List<String> names) {
        return rolRepository.findByNombreIn(names);
    }
}