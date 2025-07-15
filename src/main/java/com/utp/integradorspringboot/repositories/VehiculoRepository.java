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

    Optional<Vehiculo> findByPlaca(String placa);

    @Query("SELECT v FROM Vehiculo v JOIN FETCH v.cliente c WHERE " +
           "LOWER(v.marca) LIKE %:searchQuery% OR " +
           "LOWER(v.modelo) LIKE %:searchQuery% OR " +
           "LOWER(v.placa) LIKE %:searchQuery% OR " +
           "LOWER(c.nombres) LIKE %:searchQuery% OR " +
           "LOWER(c.apellidos) LIKE %:searchQuery% OR " +
           "LOWER(c.dni) LIKE %:searchQuery%")
    List<Vehiculo> findBySearchQuery(@Param("searchQuery") String searchQuery);

    List<Vehiculo> findByClienteId(Long clienteId);
}