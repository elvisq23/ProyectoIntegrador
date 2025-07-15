package com.utp.integradorspringboot.repository;

import com.utp.integradorspringboot.models.OrdenServicio;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenServicioRepository extends CrudRepository<OrdenServicio, Long> {
    // CrudRepository ya nos da métodos como findAll(), findById(), save(), etc.
    // No necesitamos añadir nada más por ahora.
}