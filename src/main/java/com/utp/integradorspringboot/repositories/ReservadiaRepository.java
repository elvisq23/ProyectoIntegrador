package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.Reservadia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservadiaRepository extends JpaRepository<Reservadia, Integer> {

    /*  BÚSQUEDAS PERSONALIZADAS  */

    // Buscar por nombre de vehículo (contiene texto, sin importar mayúsculas)
    List<Reservadia> findByVehiculoContainingIgnoreCase(String vehiculo);

    // Buscar por placa (contiene texto, sin importar mayúsculas)
    List<Reservadia> findByPlacaContainingIgnoreCase(String placa);
}
