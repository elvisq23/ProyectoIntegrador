package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.dto.SalidaRepuestoRequestDTO;
import com.utp.integradorspringboot.models.SalidaRepuesto;
import com.utp.integradorspringboot.models.Repuesto;
import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.repositories.SalidaRepuestoRepository;
import com.utp.integradorspringboot.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SalidaRepuestoService {

    @Autowired
    private SalidaRepuestoRepository salidaRepuestoRepository;

    @Autowired
    private RepuestoService repuestoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<SalidaRepuesto> getAllSalidas() {
        return salidaRepuestoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<SalidaRepuesto> getSalidaRepuestoById(Long id) {
        return salidaRepuestoRepository.findById(id);
    }

    @Transactional
    public SalidaRepuesto saveSalidaRepuesto(SalidaRepuestoRequestDTO request) {
        if (request.getId() != null && request.getId() != 0) {
            throw new IllegalArgumentException("No se debe proporcionar ID para la creación de una nueva salida de repuesto.");
        }

        Repuesto repuesto = repuestoService.getRepuestoById(request.getRepuestoId())
                .orElseThrow(() -> new IllegalArgumentException("Repuesto no encontrado con ID: " + request.getRepuestoId()));

        if (repuesto.getStock() < request.getCantidad()) {
            throw new IllegalArgumentException("No hay suficiente stock de " + repuesto.getNombre() + ". Stock actual: " + repuesto.getStock());
        }

        Usuario mecanico = usuarioRepository.findById(request.getMecanicoId())
                .orElseThrow(() -> new IllegalArgumentException("Mecánico no encontrado con ID: " + request.getMecanicoId()));

        SalidaRepuesto nuevaSalida = new SalidaRepuesto();
        nuevaSalida.setRepuesto(repuesto);
        nuevaSalida.setCantidad(request.getCantidad());
        nuevaSalida.setFechaSalida(LocalDateTime.now());
        nuevaSalida.setMecanico(mecanico);
        nuevaSalida.setServicioIdAsociado(request.getServicioIdAsociado());

        SalidaRepuesto savedSalida = salidaRepuestoRepository.save(nuevaSalida);

        repuestoService.updateStock(repuesto.getId(), -request.getCantidad());

        return savedSalida;
    }
}