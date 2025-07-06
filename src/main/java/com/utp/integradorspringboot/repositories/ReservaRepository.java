package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.EstadoReserva;
import com.utp.integradorspringboot.models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByEstado(EstadoReserva estado);
    List<Reserva> findByFechaReservaBetween(Date fechaInicio, Date fechaFin);
    List<Reserva> findBySedeId(Long sedeId);
}