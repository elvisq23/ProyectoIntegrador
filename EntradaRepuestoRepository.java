package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.EntradaRepuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntradaRepuestoRepository extends JpaRepository<EntradaRepuesto, Long> {

    List<EntradaRepuesto> findByProveedorContainingIgnoreCase(String proveedor);
}