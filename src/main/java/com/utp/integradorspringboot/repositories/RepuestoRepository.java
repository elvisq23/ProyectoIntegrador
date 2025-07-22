package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.Repuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepuestoRepository extends JpaRepository<Repuesto, Long> {
    Optional<Repuesto> findByNombre(String nombre);
    List<Repuesto> findByNombreContainingIgnoreCase(String nombre);
}