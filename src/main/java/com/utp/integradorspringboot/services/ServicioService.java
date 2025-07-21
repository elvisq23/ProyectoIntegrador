package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.models.Servicio;
import com.utp.integradorspringboot.repositories.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    @Transactional(readOnly = true)
    public List<Servicio> getAllServicios() {
        return servicioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Servicio> getServicioById(Long id) {
        return servicioRepository.findById(id);
    }
}
