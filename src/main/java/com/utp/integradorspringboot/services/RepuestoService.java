package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.models.Repuesto;
import com.utp.integradorspringboot.repositories.RepuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RepuestoService {

    @Autowired
    private RepuestoRepository repuestoRepository;

    @Transactional(readOnly = true)
    public List<Repuesto> getAllRepuestos() {
        return repuestoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Repuesto> getRepuestoById(Long id) {
        return repuestoRepository.findById(id);
    }

    @Transactional
    public Repuesto saveRepuesto(Repuesto repuesto) {
        return repuestoRepository.save(repuesto);
    }

    @Transactional
    public void deleteRepuesto(Long id) {
        repuestoRepository.deleteById(id);
    }
}