package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.UsuarioRol;
import com.utp.integradorspringboot.models.UsuarioRolId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, UsuarioRolId> {
    List<UsuarioRol> findByRolId(Integer rolId);
    List<UsuarioRol> findByUsuarioId(Integer usuarioId);
} 