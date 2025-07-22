package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.Repuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepuestoRepository extends JpaRepository<Repuesto, Long> {

    // Método para buscar repuestos por nombre o descripción de forma parcial e insensible a mayúsculas/minúsculas
    @Query("SELECT r FROM Repuesto r WHERE LOWER(r.nombre) LIKE %:searchQuery% OR LOWER(r.descripcion) LIKE %:searchQuery%")
    List<Repuesto> findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(@Param("searchQuery") String searchQuery);
}