package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.Llegada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LlegadaRepository extends JpaRepository<Llegada, Long> {
    List<Llegada> findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCaseOrMarcaContainingIgnoreCaseOrPlacaContainingIgnoreCase(
        String nombres, String apellidos, String marca, String placa
    );
}
