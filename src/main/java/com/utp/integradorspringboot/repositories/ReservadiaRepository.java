package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.Reservadia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservadiaRepository extends JpaRepository<Reservadia, Long> {
    
    // Permite buscar por apellidos, marca o placa que contengan el texto (ignorando mayúsculas/minúsculas)
    List<Reservadia> findByApellidosContainingIgnoreCaseOrMarcaContainingIgnoreCaseOrPlacaContainingIgnoreCase(
        String apellidos, String marca, String placa
    );
}
