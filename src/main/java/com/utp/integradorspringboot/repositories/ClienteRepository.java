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

    Optional<Cliente> findByDni(String dni);
    Optional<Cliente> findByRuc(String ruc);

    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nombres) LIKE %:searchQuery% OR " +
           "LOWER(c.apellidos) LIKE %:searchQuery% OR " +
           "LOWER(c.dni) LIKE %:searchQuery% OR " +
           "LOWER(c.telefono) LIKE %:searchQuery%")
    List<Cliente> findBySearchQuery(@Param("searchQuery") String searchQuery);
}