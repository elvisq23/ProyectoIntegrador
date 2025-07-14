/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.EstadoOrden;
import com.utp.integradorspringboot.models.OrdenServicio;
import com.utp.integradorspringboot.repositories.OrdenServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class OrdenServicioController {

    @Autowired
    private OrdenServicioRepository repository;

    @GetMapping("/ordenes")
    public ResponseEntity<List<OrdenServicio>> getAllOrdenes() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/ordenes/estado/{estado}")
    public ResponseEntity<List<OrdenServicio>> getByEstado(@PathVariable("estado") String estado) {
        try {
            EstadoOrden estadoEnum = EstadoOrden.valueOf(estado.toUpperCase());
            return new ResponseEntity<>(repository.findByEstado(estadoEnum), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // estado inválido
        }
    }

    @PostMapping("/ordenes")
    public ResponseEntity<OrdenServicio> createOrden(@RequestBody OrdenServicio orden) {
        try {
            OrdenServicio nueva = repository.save(orden);
            return new ResponseEntity<>(nueva, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/ordenes/{id}/estado")
    public ResponseEntity<OrdenServicio> cambiarEstado(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        Optional<OrdenServicio> ordenData = repository.findById(id);
        if (ordenData.isPresent()) {
            OrdenServicio orden = ordenData.get();
            try {
                EstadoOrden nuevoEstado = EstadoOrden.valueOf(body.get("estado").toUpperCase());
                orden.setEstado(nuevoEstado);
                return new ResponseEntity<>(repository.save(orden), HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // estado inválido
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
