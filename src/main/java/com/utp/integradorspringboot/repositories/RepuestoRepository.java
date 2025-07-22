package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.Repuesto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepuestoRepository extends JpaRepository<Repuesto, Integer> {
}