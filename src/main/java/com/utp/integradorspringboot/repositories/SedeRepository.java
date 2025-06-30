package com.utp.integradorspringboot.repositories; 

import com.utp.integradorspringboot.models.Sede;
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 

import java.util.List;
import java.util.Optional;

@Repository
public interface SedeRepository extends JpaRepository<Sede, Long> {
    Optional<Sede> findByNombreSede(String nombre);
    List<Sede> findByNombreSedeContaining(String nombreSede);
}