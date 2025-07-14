/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Llegada;
import com.utp.integradorspringboot.repositories.LlegadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class Registrar_llegadaController {

    @Autowired
    private LlegadaRepository repository;

    /* ──────────────────────────
       LISTAR TODAS LAS LLEGADAS
       GET /api/llegadas
    ────────────────────────── */
    @GetMapping("/llegadas")
    public ResponseEntity<List<Llegada>> getAll() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    /* ──────────────────────────
       OBTENER 1 LLEGADA POR ID
       GET /api/llegadas/{id}
    ────────────────────────── */
    @GetMapping("/llegadas/{id}")
    public ResponseEntity<Llegada> getById(@PathVariable Integer id) {
        Optional<Llegada> data = repository.findById(id);
        return data.map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /* ──────────────────────────
       CREAR NUEVA LLEGADA
       POST /api/llegadas
    ────────────────────────── */
    @PostMapping("/llegadas")
    public ResponseEntity<Llegada> create(@RequestBody Llegada llegada) {
        try {
            Llegada nuevo = repository.save(llegada);
            return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* ──────────────────────────
       ACTUALIZAR LLEGADA EXISTENTE
       PUT /api/llegadas/{id}
    ────────────────────────── */
    @PutMapping("/llegadas/{id}")
    public ResponseEntity<Llegada> update(@PathVariable Integer id,
                                          @RequestBody Llegada llegadaReq) {
        Optional<Llegada> data = repository.findById(id);
        if (data.isPresent()) {
            Llegada l = data.get();
            // Actualizar campos
            l.setCliente(llegadaReq.getCliente());
            l.setMarca(llegadaReq.getMarca());
            l.setModelo(llegadaReq.getModelo());
            l.setPlaca(llegadaReq.getPlaca());
            l.setColor(llegadaReq.getColor());
            l.setFechaHora(llegadaReq.getFechaHora());
            return new ResponseEntity<>(repository.save(l), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /* ──────────────────────────
       ELIMINAR LLEGADA
       DELETE /api/llegadas/{id}
       (opcional, por si lo necesitas)
    ────────────────────────── */
    @DeleteMapping("/llegadas/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Integer id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
