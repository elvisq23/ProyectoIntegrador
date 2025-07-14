package com.utp.integradorspringboot.repositories; 

import com.utp.integradorspringboot.models.Sede; 
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 

import java.util.List; 

@Repository
public interface SedeRepository extends JpaRepository<Sede, Long> { 
    List<Sede> findByNombreSedeContaining(String nombreSede);
}