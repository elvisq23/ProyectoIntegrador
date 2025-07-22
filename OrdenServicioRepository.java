package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.OrdenServicio;
import org.springframework.data.jpa.repository.JpaRepository; // <- IMPORTANTE: Cambiado a JpaRepository
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad OrdenServicio.
 * Se extiende JpaRepository para que findAll() devuelva una Lista y no un Iterable.
 */
@Repository
public interface OrdenServicioRepository extends JpaRepository<OrdenServicio, Long> {
    // Aquí puedes añadir métodos de consulta personalizados si los necesitas en el futuro.
}