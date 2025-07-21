package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    Optional<Servicio> findByNombre(String nombre);
    List<Servicio> findByNombreContaining(String nombre);
    List<Servicio> findByPrecioGreaterThan(BigDecimal precio);
}