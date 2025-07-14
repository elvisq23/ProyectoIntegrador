/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.Llegada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LlegadaRepository extends JpaRepository<Llegada, Integer> {

    /*  BÚSQUEDAS PERSONALIZADAS  */
    List<Llegada> findByClienteContainingIgnoreCase(String cliente);

    List<Llegada> findByPlacaContainingIgnoreCase(String placa);
}
