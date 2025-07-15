package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.models.OrdenServicio;
import com.utp.integradorspringboot.repository.OrdenServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrdenServicioServiceImpl implements OrdenServicioService {

    @Autowired
    private OrdenServicioRepository ordenServicioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<OrdenServicio> getAllOrdenesServicio() {
        return (List<OrdenServicio>) ordenServicioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdenServicio> getOrdenServicioById(Long id) {
        return ordenServicioRepository.findById(id);
    }
}