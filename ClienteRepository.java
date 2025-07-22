package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Busca un cliente por su número de DNI.
     */
    Optional<Cliente> findByDni(String dni);

    /**
     * Busca un cliente por su número de RUC.
     */
    Optional<Cliente> findByRuc(String ruc);

    /**
     * Busca clientes por un término de búsqueda en nombres, apellidos, DNI o RUC.
     */
    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nombres) LIKE %:query% OR LOWER(c.apellidos) LIKE %:query% OR c.dni LIKE %:query% OR c.ruc LIKE %:query%")
    List<Cliente> findBySearchQuery(@Param("query") String query);
}