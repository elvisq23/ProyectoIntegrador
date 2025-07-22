package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
    // Puedes agregar métodos personalizados aquí si lo necesitas
    List<Vehiculo> findByPlacaContainingIgnoreCaseOrMarcaContainingIgnoreCaseOrApellidosContainingIgnoreCase(String placa, String marca, String apellidos);
}
