package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.Rol;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
    List<Rol> findByNombreIn(List<String> nombres); 

}