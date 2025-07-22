package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Integer> {
    List<DetalleOrden> findByOrdenServicioId(Integer ordenServicioId);
} 