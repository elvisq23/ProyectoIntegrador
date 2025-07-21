/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.EstadoOrden;
import com.utp.integradorspringboot.models.OrdenServicio;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdenServicioRepository extends JpaRepository<OrdenServicio, Integer> {

    @EntityGraph(attributePaths = {"vehiculo", "diagnostico"})
    List<OrdenServicio> findByEstado(EstadoOrden estado);
}

