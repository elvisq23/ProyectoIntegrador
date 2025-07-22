package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    // Este método es para buscar por placa, es correcto y debe quedarse.
    Optional<Vehiculo> findByPlaca(String placa);

    // Este método es para la búsqueda general, también es correcto.
    @Query("SELECT v FROM Vehiculo v WHERE " +
           "LOWER(v.marca) LIKE %:query% OR " +
           "LOWER(v.modelo) LIKE %:query% OR " +
           "LOWER(v.placa) LIKE %:query% OR " +
           "LOWER(v.propietarioNombres) LIKE %:query% OR " +
           "LOWER(v.propietarioApellidos) LIKE %:query%")
    List<Vehiculo> findBySearchQuery(@Param("query") String query);
    
    // --- MÉTODO ELIMINADO ---
    // El siguiente método es el que causaba el error y ha sido eliminado:
    // List<Vehiculo> findByClienteId(Long clienteId); 
}