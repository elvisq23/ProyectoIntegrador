package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.models.Reserva;
import com.utp.integradorspringboot.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Transactional(readOnly = true)
    public List<Reserva> getAllReservas() {
        return reservaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Reserva> getReservaById(Long id) {
        return reservaRepository.findById(id);
    }

    @Transactional
    public Reserva createReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    @Transactional
    public Reserva updateReserva(Long id, Reserva reserva) {
        Reserva existingReserva = getReservaById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        
        existingReserva.setVehiculo(reserva.getVehiculo());
        existingReserva.setSede(reserva.getSede());
        existingReserva.setEstado(reserva.getEstado());
        existingReserva.setFechaReserva(reserva.getFechaReserva());
        existingReserva.setHoraReserva(reserva.getHoraReserva());
        existingReserva.setComentario(reserva.getComentario());
        
        return reservaRepository.save(existingReserva);
    }

    @Transactional
    public void deleteReserva(Long id) {
        reservaRepository.deleteById(id);
    }
}
