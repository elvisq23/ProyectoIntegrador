package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.dto.EntradaRepuestoRequestDTO;
import com.utp.integradorspringboot.models.EntradaRepuesto;
import com.utp.integradorspringboot.models.Repuesto;
import com.utp.integradorspringboot.repositories.EntradaRepuestoRepository;
import com.utp.integradorspringboot.repositories.RepuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EntradaRepuestoService {

    @Autowired
    private EntradaRepuestoRepository entradaRepuestoRepository;

    @Autowired
    private RepuestoService repuestoService;

    @Transactional(readOnly = true)
    public List<EntradaRepuesto> getAllEntradas() {
        return entradaRepuestoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<EntradaRepuesto> getEntradaRepuestoById(Long id) {
        return entradaRepuestoRepository.findById(id);
    }

    @Transactional
    public EntradaRepuesto saveEntradaRepuesto(EntradaRepuestoRequestDTO request) {
        if (request.getId() != null && request.getId() != 0) {
            throw new IllegalArgumentException("No se debe proporcionar ID para la creación de una nueva entrada de repuesto.");
        }

        Repuesto repuesto = repuestoService.getRepuestoById(request.getRepuestoId())
                .orElseThrow(() -> new IllegalArgumentException("Repuesto no encontrado con ID: " + request.getRepuestoId()));

        EntradaRepuesto nuevaEntrada = new EntradaRepuesto();
        nuevaEntrada.setRepuesto(repuesto);
        nuevaEntrada.setCantidad(request.getCantidad());
        nuevaEntrada.setFechaEntrada(LocalDateTime.now());
        nuevaEntrada.setProveedor(request.getProveedor());

        EntradaRepuesto savedEntrada = entradaRepuestoRepository.save(nuevaEntrada);

        repuestoService.updateStock(repuesto.getId(), request.getCantidad());

        return savedEntrada;
    }
}